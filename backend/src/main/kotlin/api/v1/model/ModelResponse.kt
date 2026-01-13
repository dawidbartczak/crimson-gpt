package dev.noctis.api.v1.model

import dev.noctis.postgres.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ModelResponse(
    val prediction: String,
    @Serializable(with = UUIDSerializer::class) val chatId: UUID? = null
)
