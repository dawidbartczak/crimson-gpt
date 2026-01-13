package dev.noctis.postgres.table.messages

import kotlinx.serialization.Serializable
import dev.noctis.postgres.util.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import java.util.UUID

@Serializable
data class Message(
    val content: String,
    val author: Int,
    val createdAt: LocalDateTime? = null,
    @Serializable(with = UUIDSerializer::class) val chatId: UUID,
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
)
