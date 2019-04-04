package com.github.eren.wyagkt.models

class GitBlob(override val type : String, override val contents : String) : GitObject {
    val escapeChar = '\u0000'

    override fun deserialize() : String {
        return contents.split(escapeChar)[1]
    }

    override fun serialize() : String {
        return contents
    }
}