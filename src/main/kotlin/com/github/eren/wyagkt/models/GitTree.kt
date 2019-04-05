package com.github.eren.wyagkt.models

/**
 * Class to hold git tree objects
 */
class GitTree(override val size : Int,
              override val contents : String,
              override val type : String = "tree"
) : GitObject {

    override fun deserialize(): String {
        return contents
    }

    override fun serialize() : String {
        TODO("not implemented")
    }
}