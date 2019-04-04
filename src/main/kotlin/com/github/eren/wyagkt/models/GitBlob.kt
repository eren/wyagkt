package com.github.eren.wyagkt.models

class GitBlob(override val type : String, override val contents : String) : GitObject {
    override fun deserialize() {
        val escapeChar = '\u0000'

        val splitted = contents.split(escapeChar)
        print(splitted[1])
    }

    override fun serialize() {
        TODO("unimplemented")
    }
}