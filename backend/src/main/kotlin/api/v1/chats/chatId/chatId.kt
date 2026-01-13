package dev.noctis.api.v1.chats.chatId

import dev.noctis.api.v1.chats.ChatsRequest
import dev.noctis.postgres.table.chats.Chat
import dev.noctis.postgres.table.chats.ChatsService
import dev.noctis.postgres.util.toUUID
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import org.koin.ktor.plugin.scope
import kotlin.getValue

fun Route.chatId() {
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

        patch {
            val chatsService by call.scope.inject<ChatsService>()

            val userId = call.principal<JWTPrincipal>()?.subject?.toUUID()
                ?: return@patch call.respond(HttpStatusCode.Unauthorized, "Authentication required")

            val chatId = call.parameters["chatId"]?.toUUID()
                ?: return@patch call.respond(HttpStatusCode.BadRequest, "Invalid or missing chat ID")

            val (title) = call.receiveNullable<ChatsRequest>()
                ?: return@patch call.respond(HttpStatusCode.BadRequest, "Invalid request body")

            val chat = chatsService.selectByUserIdAndChatId(userId, chatId)
                ?: return@patch call.respond(HttpStatusCode.Forbidden, "You do not have access to this chat")

            val count = chatsService.updateTitleByChatId(chatId, title)

            if (count > 0)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(HttpStatusCode.NotFound, "Chat not found or no changes made")
        }

        delete {
            val chatsService by call.scope.inject<ChatsService>()

            val userId = call.principal<JWTPrincipal>()?.subject?.toUUID()
                ?: return@delete call.respond(HttpStatusCode.Unauthorized, "Authentication required")

            val chatId = call.parameters["chatId"]?.toUUID()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid or missing chat ID")

            val chat = chatsService.selectByUserIdAndChatId(userId, chatId)
                ?: return@delete call.respond(HttpStatusCode.Forbidden, "You do not have access to this chat")

            val count = chatsService.deleteByChatId(chatId)

            if (count > 0)
                call.respond(HttpStatusCode.OK, "Chat deleted successfully")
            else
                call.respond(HttpStatusCode.NotFound, "Chat not found")
        }
    }
}