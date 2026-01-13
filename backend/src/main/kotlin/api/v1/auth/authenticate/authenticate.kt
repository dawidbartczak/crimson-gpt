package dev.noctis.api.v1.auth.authenticate

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authenticate() {
    authenticate {
        get {
            call.respond(HttpStatusCode.OK)
        }
    }
}