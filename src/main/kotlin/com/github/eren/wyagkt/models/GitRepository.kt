package com.github.eren.wyagkt.models

import com.github.eren.wyagkt.exceptions.ObjectNotFoundException
import com.github.eren.wyagkt.exceptions.UnknownObjectTypeException
import com.github.eren.wyagkt.utils.decompress
import java.io.File
import java.nio.file.Paths
import org.ini4j.Wini

/**
 * Class to hold .git repository as an object
 *
 * This class requires a work tree and derives git directory by appending .git to
 * the directory
 *
 * It requires absolute paths to work.
 *
 * @param workTree absolute working tree
 */
class GitRepository(workTree: String) {
    val gitDir: String = Paths.get(workTree, ".git").toString()

    /**
     * Initialize git repository
     *
     * We assume that this method is called on a directory where .git does not exist
     */
    fun initialize() {
        File(gitDir).mkdirs()
        File(repoPath("branches")).mkdirs()
        File(repoPath("objects")).mkdirs()
        File(repoPath("refs", "tags")).mkdirs()
        File(repoPath("refs", "heads")).mkdirs()

        File(repoPath("description")).writeText("Unnamed repository; edit this file 'description' to name the repository.\n")
        File(repoPath("HEAD")).writeText("ref: refs/heads/master\n")
        File(repoPath("config")).writeText("")

        val gitConfig = Wini(File(repoPath("config")))
        gitConfig.put("core", "repositoryformatversion", "0")
        gitConfig.put("core", "filemode", "false")
        gitConfig.put("core", "bare", "false")
        gitConfig.store()
    }

    /**
     * Helper method to easily access the repository paths.
     *
     * Example:
     *
     *  repoDir("refs", "tags") returns $workTree/.git/refs/tags
     *
     * @param params String: variable arguments
     * @return String
     */
    private fun repoPath(vararg params: String): String {
        return Paths.get(gitDir, *params).toString()
    }


    /**
     * Check if the repository is a valid git repo
     *
     * This method checks if .git exists
     */
    fun isValid(): Boolean {
        val gitDirectory = File(gitDir)

        if (!gitDirectory.exists() || !gitDirectory.isDirectory) {
            return false
        }

        // TODO: check repository version. For a small project, this is enough for now.

        return true
    }

    /**
     * Read an object from the repository
     *
     * Git stores files with .git/objects/{2}/{36} format deduced from the sha1sum of the object. This method
     * understands the shorthand form and it tries to find the object from the given hash.
     *
     * @param sha1sum String: (partial) sha1sum of the object. Value must be greater than 4
     * @return GitObject
     * @throws ObjectNotFoundException
     * @throws UnknownObjectTypeException
     */
    fun objectRead(sha1sum: String) : GitObject {
        val obj = findObject(sha1sum)
        val contents = decompress(obj)
        // Unfortunately \00 is not a valid escape sequence, we need to work around it
        val escapeChar = '\u0000'

        val splitted = contents.split(escapeChar)
        val objectContent : String = splitted.last()

        val typeAndSize = splitted.first()
        val type : String = typeAndSize.split(' ').first()
        val size : Int = typeAndSize.split(' ').last().toInt()

        // TODO: check for the object size, we do not need it for now.

        when (type) {
            "blob" -> return GitBlob(size, objectContent)
            "tree" -> return GitTree(size, objectContent)
        }

        throw UnknownObjectTypeException(sha1sum, type)
    }

    /**
     * Given sha1sum partial or full, find the object in .objects directory.
     *
     * Example:
     *  findObject("abcd1234") -> /path/to/.git/objects/ab/cd12345bcdf
     *
     * @param sha1sum String (partial) sha1sum of the object.
     * @return Full path of the object in the repository
     * @throws ObjectNotFoundException
     */
    private fun findObject(sha1sum: String): String {
        val first = sha1sum.substring(0, 2)
        val rest = sha1sum.substring(2, sha1sum.length)
        val fullObjectPath = repoPath("objects", first, rest)

        if (File(fullObjectPath).exists()) {
            // trivial case
            return fullObjectPath
        } else {
            // This maybe a partial match, look for the first directory and we will iterate later if found.
            val firstDirectory = repoPath("objects", first)

            if (!File(firstDirectory).exists()) {
                // We cannot find the directory, it doesn't exist, no more to lookup
                throw ObjectNotFoundException(sha1sum)
            }

            // We know that we have a partial match, iterate over the directory and find the file.
            File(firstDirectory).walk().forEach {
                val fileName = it.toString().split('/').last()
                if (fileName.startsWith(rest)) {
                    return repoPath("objects", first, fileName)
                }
            }
        }

        throw ObjectNotFoundException(sha1sum)
    }
}