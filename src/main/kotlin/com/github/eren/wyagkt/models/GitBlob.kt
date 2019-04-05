package com.github.eren.wyagkt.models

/**
 * Class to hold git blob objects
 */
class GitBlob(override val size: Int,
              override val contents : String,
              override val type : String = "blob"
) : GitObject {
    private val escapeChar = "\u0000"

    override fun deserialize() : String {
        return contents
    }

    override fun serialize() : String {
        return "$type $size$escapeChar$contents"
    }
}