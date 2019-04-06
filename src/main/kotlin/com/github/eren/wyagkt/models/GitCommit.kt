package com.github.eren.wyagkt.models

/**
 * Class to hold git tree objects
 */
class GitCommit(override val size : Int,
              override val contents : String,
              override val type : String = "commit"
) : GitObject {

    override fun deserialize(): String {
        return contents
    }

    override fun serialize() : String {
        TODO("not implemented")
    }
}