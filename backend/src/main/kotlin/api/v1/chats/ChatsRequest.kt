package dev.noctis.api.v1.chats

import kotlinx.serialization.Serializable

@Serializable
data class ChatsRequest(
    val title: String
)
