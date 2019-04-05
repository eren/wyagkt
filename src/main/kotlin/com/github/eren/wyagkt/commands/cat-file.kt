package com.github.eren.wyagkt.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.eren.wyagkt.exceptions.NotAGitRepositoryException
import com.github.eren.wyagkt.exceptions.ObjectNotFoundException
import com.github.eren.wyagkt.exceptions.UnknownObjectTypeException
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.provider
import com.github.eren.wyagkt.models.GitRepository
import com.github.eren.wyagkt.utils.repoFind

/**
 * Cat the blob from its object hash
 */
class `Cat-File` : CliktCommand(
    help = """Reads an object from the repository
        |
        | OBJECT is the sha1sum of the object, minimum 4 characters
        |
    """.trimMargin()) {

    private val `object` by argument().validate {
        require( it.length > 4 ) {
            "object hash must be greater than 4"
        }
    }

    override fun run() {
        try {
            val repo: GitRepository = repoFind()
            val obj = repo.objectRead(`object`)
            print(obj.deserialize())

        } catch (e: Exception) {
            when (e) {
                is NotAGitRepositoryException -> { echo(e.message) }
                is ObjectNotFoundException -> { echo(e.message) }
                is UnknownObjectTypeException -> { echo(e.message) }
                else -> throw e
            }
        }
    }
}

val catFileModule = Kodein.Module("catFile") {
    bind<CliktCommand>().inSet() with provider { `Cat-File`() }
}