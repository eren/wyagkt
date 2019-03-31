package com.github.eren.wyagkt.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.provider
import com.github.eren.wyagkt.models.GitRepository
import com.github.eren.wyagkt.exceptions.NotAGitRepositoryException

class Init : CliktCommand(
    help = """Initializes a git repository""") {
    private val repo: String by option("-r", "--repository", help="repository location (default: .)")
        .default(".")

    override fun run() {
        echo("Initializing repository $repo")

        try {
            val gitRepository = GitRepository(".")
            echo(gitRepository.gitDir)
        } catch (e: NotAGitRepositoryException) {
            echo("Error: not a git repository")
        }
    }
}

val initModule = Kodein.Module("init") {
    bind<CliktCommand>().inSet() with provider { Init() }
}