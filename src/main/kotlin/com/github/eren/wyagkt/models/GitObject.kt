package com.github.eren.wyagkt.models

/**
 * Common interface representing git objects
 *
 * @param contents zlib decompressed content of the object
 * @param type type of the object (blob, tree, commit)
 */
interface GitObject {
    val contents : String
    val type: String

    fun serialize()
    fun deserialize()
}