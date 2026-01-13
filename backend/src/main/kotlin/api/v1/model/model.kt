 package api.v1.model

import dev.noctis.api.v1.model.ModelResponse
import dev.noctis.gpt.GptService
import dev.noctis.postgres.table.chats.Chat
import dev.noctis.postgres.table.chats.ChatsService
import dev.noctis.postgres.table.messages.Message
import dev.noctis.postgres.table.messages.MessagesService
import dev.noctis.postgres.util.toUUID
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.koin.ktor.plugin.scope

fun Route.model() {
    authenticate(optional = true) {
        post {
            val gptService by call.scope.inject<GptService>()
            val chatsService by call.scope.inject<ChatsService>()
            val messagesService by call.scope.inject<MessagesService>()

            val userId = call.principal<JWTPrincipal>()?.subject?.toUUID()

            val request = call.receiveNullable<ModelRequest>() ?: run {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid request body")

                return@post
            }

            val (context, chatId) = request

            when {
                // Post message on already existing chat (if it belongs to the user).
                userId != null && chatId != null -> {
                    // Check if the chat belongs to the user.
                    chatsService.selectByUserIdAndChatId(userId, chatId) ?: run {
                        call.respond(HttpStatusCode.Forbidden, "You do not have permission to access this chat")

                        return@post
                    }

                    val prediction = gptService.generate(context)

                    val prompt = Message(content = context, author = 0, chatId = chatId)
                    val reply = Message(content = prediction, author = 1, chatId = chatId)

                    messagesService.insert(prompt)
                    messagesService.insert(reply)

                    call.respond(HttpStatusCode.OK, ModelResponse(prediction, chatId))
                }

                // Create a new chat and post message on it
                userId != null -> {
                    val chat = Chat(
                        title = "New chat",
                        userId = userId
                    )

                    val chatId = chatsService.insert(chat)

                    val prediction = gptService.generate(context)

                    val prompt = Message(content = context, author = 0, chatId = chatId)
                    val reply = Message(content = prediction, author = 1, chatId = chatId)

                    messagesService.insert(prompt)
                    messagesService.insert(reply)

                    call.respond(HttpStatusCode.Created, ModelResponse(prediction, chatId))
                }

                // Unauthorised
                chatId != null -> {
                    call.respond(HttpStatusCode.Forbidden, "You do not have permission to access this chat")
                }

                // Send prediction
                else -> {
                    val prediction = gptService.generate(context)

                    call.respond(HttpStatusCode.OK, ModelResponse(prediction))
                }
            }
        }
    }
}