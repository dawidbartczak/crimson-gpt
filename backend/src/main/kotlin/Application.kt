package dev.noctis

import dev.noctis.plugin.configureJwt
import dev.noctis.plugin.configureKoin
import dev.noctis.plugin.configureRouting
import dev.noctis.plugin.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureJwt()
    configureSerialization()
    configureRouting()
}
