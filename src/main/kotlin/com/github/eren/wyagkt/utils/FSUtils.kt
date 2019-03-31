package com.github.eren.wyagkt.utils

import com.github.eren.wyagkt.models.GitRepository


/**
 * Recursively finds a .git directory
 *
 * @param String directory: directory to recursively find .git directory
 * @return GitRepository
 */
fun repo_find(workTree: String) : GitRepository {
    return GitRepository(workTree)

}