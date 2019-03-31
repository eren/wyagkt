package com.github.eren.wyagkt.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.provider
import java.nio.file.Paths
import com.github.eren.wyagkt.models.GitRepository
import com.github.eren.wyagkt.exceptions.NotAGitRepositoryException
import com.github.eren.wyagkt.utils.repoFind

class Init : CliktCommand(
    help = """Initializes a git repository""") {
    private val repo: String by option("-r", "--repository", help="repository location (default: .)")
        .default("")

    override fun run() {
        try {
            val repo: GitRepository = repoFind(Paths.get("").toAbsolutePath().toString())
            if (repo.isValid()) {
                echo("Repository already initialized. Skipping")
            }
        } catch (e: NotAGitRepositoryException) {
            val workTree = Paths.get(repo).toAbsolutePath().toString()
            val gitRepository = GitRepository(workTree)

            echo("Initializing git repository at ${gitRepository.gitDir}")

            gitRepository.initialize()
        }
    }
}

val initModule = Kodein.Module("init") {
    bind<CliktCommand>().inSet() with provider { Init() }
}