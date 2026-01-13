package dev.noctis.api.v1.chats

import dev.noctis.postgres.table.chats.Chat
import dev.noctis.postgres.table.chats.ChatsService
import dev.noctis.postgres.util.toUUID
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.scope

fun Route.chats() {
    authenticate {
        post {
            val chatsService by call.scope.inject<ChatsService>()

            val userId = call.principal<JWTPrincipal>()?.subject?.toUUID()
                ?: return@post call.respond(HttpStatusCode.Unauthorized, "Authentication required")

            val (title) = call.receiveNullable<ChatsRequest>()
                ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid request body")

            val chat = Chat(title = title, userId = userId)

            val id = chatsService.insert(chat)

            call.respond(HttpStatusCode.Created, chat.copy(id = id))
        }

        get {
            val chatsService by call.scope.inject<ChatsService>()

            val userId = call.principal<JWTPrincipal>()?.subject?.toUUID()
                ?: return@get call.respond(HttpStatusCode.Unauthorized, "Authentication required")

            val chats = chatsService.selectAllByUserId(userId)

            call.respond(HttpStatusCode.OK, chats)
        }
    }
}