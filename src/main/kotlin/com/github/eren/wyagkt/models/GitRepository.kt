package com.github.eren.wyagkt.models

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
    val gitDir : String = Paths.get(workTree, ".git").toString()

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
    private fun repoPath(vararg params: String) : String {
        return Paths.get(gitDir, *params).toString()
    }

    /**
     * Check if the repository is a valid git repo
     *
     * This method checks if .git exists
     */
    fun isValid() : Boolean {
        val gitDirectory = File(gitDir)

        if (!gitDirectory.exists() || !gitDirectory.isDirectory) {
            return false
        }

        return true
    }
}