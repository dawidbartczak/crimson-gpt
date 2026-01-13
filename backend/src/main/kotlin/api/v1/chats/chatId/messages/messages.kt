package dev.noctis.api.v1.chats.chatId.messages

import dev.noctis.api.v1.chats.ChatsRequest
import dev.noctis.postgres.table.chats.Chat
import dev.noctis.postgres.table.chats.ChatsService
import dev.noctis.postgres.table.messages.MessagesService
import dev.noctis.postgres.util.toUUID
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.koin.ktor.plugin.scope

fun Route.messages() {
    authenticate {
        get {
            val chatsService = call.scope.get<ChatsService>()
            val messagesService = call.scope.get<MessagesService>()

            val userId = call.principal<JWTPrincipal>()?.subject?.toUUID()
                ?: return@get call.respond(HttpStatusCode.Unauthorized, "Missing or invalid authentication")

            val chatId = call.parameters["chatId"]?.toUUID()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid or missing chat ID")

            val chat = chatsService.selectByUserIdAndChatId(userId, chatId)
                ?: return@get call.respond(HttpStatusCode.Forbidden, "You do not have access to this chat")

            val messages = messagesService.selectAllByChatId(chatId)

            call.respond(HttpStatusCode.OK, messages)
        }
    }
}