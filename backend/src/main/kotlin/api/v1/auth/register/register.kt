package api.v1.auth.register

import dev.noctis.jwt.hashing.HashingService
import dev.noctis.postgres.table.users.User
import dev.noctis.postgres.table.users.UsersService
import io.ktor.http.*
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.scope

fun Route.register() {
    post {
        val registerValidator = call.scope.get<RegisterValidator>()
        val hashingService = call.scope.get<HashingService>()
        val usersService = call.scope.get<UsersService>()

        val request = call.receiveNullable<RegisterRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)

            return@post
        }

        val (email, username, password) = request

        registerValidator.validateEmail(email, this)
        registerValidator.validateUsername(username, this)
        registerValidator.validatePassword(password, this)

        if (call.response.status() != null) {
            return@post
        }

        val salt = hashingService.generateSalt()

        val hash = hashingService.generateHash(password, salt)

        val user = User(email, username, salt, hash)

        usersService.create(user)

        call.respond(HttpStatusCode.Created)
    }
}