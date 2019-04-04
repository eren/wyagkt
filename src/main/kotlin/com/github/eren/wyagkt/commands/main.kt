package com.github.eren.wyagkt.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.setBinding

class Cli : CliktCommand(
    help = """wyagkt is a git implementation written from scratch with Kotlin""") {
    init {
        versionOption("1.0")
    }

    override fun run() { }
}

/**
 * Application main
 */
fun main(args: Array<String>) {
    val kodein = Kodein {
        bind() from setBinding<CliktCommand>()
        import(initModule)
        import(catFileModule)
        import(hashObjectModule)
    }

    val commands: Set<CliktCommand> by kodein.instance()

    Cli().subcommands(commands).main(args)
}