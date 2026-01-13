package dev.noctis.api.v1.chats.chatId

import kotlinx.serialization.Serializable

@Serializable
data class ChatIdRequest(
    val title: String
)
