package dev.noctis.api.v1.users

import dev.noctis.postgres.table.users.UsersService
import dev.noctis.postgres.util.toUUID
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.koin.ktor.plugin.scope
import kotlin.getValue

fun Route.users() {
    authenticate {
        get {
            val userId = call.principal<JWTPrincipal>()?.subject?.toUUID()
                ?: return@get call.respond(HttpStatusCode.Unauthorized, "Authentication required")

            val userService by call.scope.inject<UsersService>()

            val user = userService.readByUserId(userId)
                ?: return@get call.respond(HttpStatusCode.Conflict)

            call.respond(HttpStatusCode.OK, user.username)
        }
    }
}