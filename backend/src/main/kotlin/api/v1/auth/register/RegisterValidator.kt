package api.v1.auth.register

import dev.noctis.postgres.table.users.UsersService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

class RegisterValidator(private val usersService: UsersService) {

    private companion object {
        val emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$".toRegex()

        /*
        ^                              Start anchor
        [a-zA-Z0-9_]                   Ensure string only contains letters (uppercase or lowercase), numbers, and underscores.
        {3,30}                         Ensure string has a length of at least 3 characters and at most 30 characters.
        $                              End anchor
         */
        val usernameRegex = "^[a-zA-Z0-9_]{3,30}$".toRegex()

        /*
        ^                              Start anchor
        (?=(.*[a-z]){3,})              Lowercase letters. {3,} indicates that you want 3 of this group
        (?=(.*[A-Z]){2,})              Uppercase letters. {2,} indicates that you want 2 of this group
        (?=(.*[0-9]){2,})              Numbers. {2,} indicates that you want 2 of this group
        (?=(.*[!@#$%^&*()\-__+.]){1,}) All the special characters in the [] fields. The ones used by regex are escaped by using the \ or the character itself. {1,} is redundant, but good practice, in case you change that to more than 1 in the future. Also keeps all the groups consistent
        {8,}                           Indicates that you want 8 or more
        $
         */
        val passwordRegex = "^(?=(.*[a-z]){1,})(?=(.*[A-Z]){1,})(?=(.*[0-9]){1,})(?=(.*[!@#\$%^&*()\\-__+.]){1,}).{8,}\$".toRegex()
    }

    suspend fun validateEmail(email: String, context: RoutingContext) {
        val emailUsed = usersService.readByEmail(email) != null
        val emailInvalid = !email.matches(emailRegex)

        if (emailUsed) {
            context.call.respond(HttpStatusCode.Conflict, "E-mail is already registered.")

            return
        }

        if (emailInvalid) {
            context.call.respond(HttpStatusCode.Conflict, "Invalid e-mail format.")

            return
        }
    }

    suspend fun validateUsername(username: String, context: RoutingContext) {
        val usernameInvalid = !username.matches(usernameRegex)

        if (usernameInvalid) {
            context.call.respond(HttpStatusCode.Conflict, "Username must be between 3 and 30 characters long and can only contain letters, numbers, and underscores.")

            return
        }
    }

    suspend fun validatePassword(password: String, context: RoutingContext) {
        val passwordInvalid = !password.matches(passwordRegex)

        if (passwordInvalid) {
            context.call.respond(HttpStatusCode.Conflict, "Password must contain at least one lowercase letter, one uppercase letter, one digit, one special character, and be at least 8 characters long.")

            return
        }
    }
}