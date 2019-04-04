package com.github.eren.wyagkt.utils

import java.nio.file.Paths
import java.nio.charset.StandardCharsets.UTF_8
import java.io.File
import com.github.eren.wyagkt.exceptions.NotAGitRepositoryException
import com.github.eren.wyagkt.models.GitRepository
import java.io.ByteArrayOutputStream
import java.util.zip.*

/**
 * Finds a .git directory by iterating over the path
 *
 * @return GitRepository representing .git directory
 * @throws NotAGitRepositoryException
 */
fun repoFind() : GitRepository {
    val workTree: String = Paths.get("").toAbsolutePath().toString()

    var gitRepository = GitRepository(workTree)
    var parent = Paths.get(workTree).parent

    if (gitRepository.isValid()) {
        return gitRepository
    }

    while (parent.toString() != "/") {
        gitRepository = GitRepository(parent.toString())

        if (gitRepository.isValid()) {
            return gitRepository
        } else {
            parent = parent.parent
        }
    }

    throw NotAGitRepositoryException()
}

/**
 * Zlib decompress a file and return its contents
 *
 * @param filePath absolute path to file
 * @return unzipped file contents
 */
fun decompress(filePath: String) : String {
    val content = File(filePath).readBytes()
    val inflater = Inflater()
    val outputStream = ByteArrayOutputStream()
    val buffer = ByteArray(1024)

    inflater.setInput(content)

    while (!inflater.finished()) {
        val count = inflater.inflate(buffer)
        outputStream.write(buffer, 0, count)
    }

    outputStream.close()

    return outputStream.toString(UTF_8)
}