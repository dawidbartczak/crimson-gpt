package dev.noctis.postgres.table.users

import dev.noctis.postgres.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    val email: String,
    val username: String,
    val salt: String,
    val hash: String,
    @Serializable(with = UUIDSerializer::class) val id: UUID? = null,
)