package api.v1.auth.login

import dev.noctis.jwt.JwtService
import dev.noctis.jwt.hashing.HashingService
import dev.noctis.postgres.table.users.UsersService
import io.ktor.http.*
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.koin.ktor.plugin.scope

fun Route.login() {
    post {
        val hashingService = call.scope.get<HashingService>()
        val usersService = call.scope.get<UsersService>()
        val jwtService = call.scope.get<JwtService>()

        val request = call.receiveNullable<LoginRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)

            return@post
        }

        val (email, password) = request

        val user = usersService.readByEmail(email) ?: run {
            call.respond(HttpStatusCode.Unauthorized)

            return@post
        }

        val verified = hashingService.verify(password, user.salt, user.hash)

        if (!verified) {
            call.respond(HttpStatusCode.Unauthorized)

            return@post
        }

        val token = jwtService.generateToken(user.id.toString())

        call.respond(HttpStatusCode.OK, LoginResponse(token))
    }
}