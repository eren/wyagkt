package com.github.eren.wyagkt.models

/**
 * Common interface representing git objects
 *
 * @param contents zlib decompressed content of the object
 * @param type type of the object (blob, tree, commit)
 */
interface GitObject {
    val type: String
    val size: Int
    val contents : String

    fun serialize() : String
    fun deserialize() : String
}