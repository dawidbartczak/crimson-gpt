package dev.noctis.postgres

data class PostgresConfig(
    val database: String,
    val user: String,
    val password: String,
    val host: String,
    val post: String,
)