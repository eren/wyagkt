package com.github.eren.wyagkt.models

/**
 * Class to hold git tree objects
 */
class GitTree(override val size : Int,
              override val contents : String,
              override val type : String = "tree"
) : GitObject {

    override fun deserialize(): String {
        // TODO: properly deserialize the data. Content here is a raw git tree object with \0 separated.
        return contents
    }

    override fun serialize() : String {
        TODO("not implemented")
    }
}