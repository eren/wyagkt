package com.github.eren.wyagkt.models

import java.io.File
import com.github.eren.wyagkt.exceptions.NotAGitRepositoryException

class GitRepository(workTree: String) {
    val gitDir = "$workTree/.git"
    val ini = null

    init {
        val file = File(gitDir)

        if (!file.exists() || !file.isDirectory) {
            throw NotAGitRepositoryException()
        }
    }
}