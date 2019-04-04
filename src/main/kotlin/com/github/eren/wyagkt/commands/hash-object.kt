package com.github.eren.wyagkt.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import com.github.eren.wyagkt.exceptions.NotAGitRepositoryException
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.provider
import com.github.eren.wyagkt.models.GitRepository
import com.github.eren.wyagkt.utils.repoFind

class `Hash-Object` : CliktCommand(
    help = """Hashes the object and optionally writes the file""") {

    private val file by argument().file(exists=true)

    override fun run() {
        try {
            val repo : GitRepository = repoFind()

            echo("working on ${repo.gitDir}")
        } catch (e: NotAGitRepositoryException) {
            echo("Error: not a git repository")
        }
    }
}

val hashObjectModule = Kodein.Module("hashObject") {
    bind<CliktCommand>().inSet() with provider { `Hash-Object`() }
}