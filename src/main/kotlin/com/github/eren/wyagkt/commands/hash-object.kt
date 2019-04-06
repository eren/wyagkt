package com.github.eren.wyagkt.commands

import java.io.File
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.inSet
import org.kodein.di.generic.provider
import com.github.eren.wyagkt.models.GitBlob
import com.github.eren.wyagkt.models.GitObject
import com.github.eren.wyagkt.utils.sha1sum

class `Hash-Object` : CliktCommand(
    help = """Hashes the object and optionally writes the file""") {

    private val file by argument().file(exists=true)
    // TODO: implement object write for blob. This will include zlib compressing the content before writing.
    // private val write by option("-w", "--write", help = "actually write to the repository").flag()

    override fun run() {
        val fileContents = File(file.toString()).readText()
        val size = fileContents.length
        val gitObject : GitObject = GitBlob(size, fileContents)
        println(gitObject.serialize().sha1sum)
    }
}

val hashObjectModule = Kodein.Module("hashObject") {
    bind<CliktCommand>().inSet() with provider { `Hash-Object`() }
}