package com.github.eren.wyagkt.utils

import com.github.eren.wyagkt.exceptions.NotAGitRepositoryException
import java.nio.file.Paths
import com.github.eren.wyagkt.models.GitRepository

/**
 * Finds a .git directory by iterating over the path
 *
 * @param workTree String: absolute path to recursively find .git
 * @return GitRepository
 */
fun repoFind(workTree: String = Paths.get("").toAbsolutePath().toString()) : GitRepository {
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