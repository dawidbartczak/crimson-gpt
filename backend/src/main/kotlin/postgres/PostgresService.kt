package dev.noctis.postgres

import org.jetbrains.exposed.sql.Database

class PostgresService(private val postgresConfig: PostgresConfig) {

    private val database = run {
        val (database, user, password, host, port) = postgresConfig

        val url = "jdbc:postgresql://$host:$port/$database"

        Database.connect(
            url = url,
            user = user,
            password = password,
            driver = "org.postgresql.Driver",
        )
    }

    fun getDatabase() = database
}