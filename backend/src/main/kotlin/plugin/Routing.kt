package dev.noctis.plugin

import api.v1.auth.login.login
import api.v1.auth.register.register
import api.v1.model.model
import dev.noctis.api.v1.auth.authenticate.authenticate
import dev.noctis.api.v1.chats.chatId.chatId
import dev.noctis.api.v1.chats.chatId.messages.messages
import dev.noctis.api.v1.chats.chats
import dev.noctis.api.v1.users.users
import io.ktor.http.ContentType
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.uri
import io.ktor.server.response.respondText
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(CORS) {
        anyHost()
        anyMethod()
        allowCredentials = true
        allowNonSimpleContentTypes = true
        allowHeaders { true }
    }

    routing {
        staticResources("/", "static", index = "index.html")

        route("api") {
            route("v1") {
                route("model") { model() }

                route("auth") {
                    route("register") { register() }
                    route("login") { login() }
                    route("authenticate") { authenticate() }
                }

                route("users") {
                    users()
                }

                route("chats") {
                    chats()

                    route("{chatId}") {
                        chatId()

                        route("messages") {
                            messages()
                        }
                    }
                }
            }
        }

        get("{...}") {
            val requestedPath = call.request.uri

            when {
                    requestedPath.endsWith("js") -> {
                        val resource = this::class.java.classLoader.getResource("static${requestedPath}")?.readText() ?: return@get

                        call.respondText(resource, ContentType.Text.JavaScript)
                    }
                    requestedPath.endsWith("css") -> {
                        val resource = this::class.java.classLoader.getResource("static${requestedPath}")?.readText() ?: return@get

                        call.respondText(resource, ContentType.Text.CSS)
                    }
                else -> {
                    val resource = this::class.java.classLoader.getResource("static/index.html")?.readText() ?: return@get

                    call.respondText(resource, ContentType.Text.Html)
                }
            }
        }
    }
}