package dev.noctis.postgres.table.chats

import dev.noctis.postgres.util.UUIDSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Chat(
    val title: String,
    val createdAt: LocalDateTime? = null,
    @Serializable(with = UUIDSerializer::class) val userId: UUID,
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
)
