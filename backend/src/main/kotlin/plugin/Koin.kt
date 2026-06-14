package dev.noctis.plugin

import api.v1.auth.register.RegisterValidator
import dev.noctis.gpt.GptService
import dev.noctis.jwt.JwtConfig
import dev.noctis.jwt.JwtService
import dev.noctis.jwt.hashing.HashingConfig
import dev.noctis.jwt.hashing.HashingService
import dev.noctis.postgres.PostgresConfig
import dev.noctis.postgres.PostgresService
import dev.noctis.postgres.table.chats.ChatsService
import dev.noctis.postgres.table.messages.MessagesService
import dev.noctis.postgres.table.users.UsersService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()

        modules(
            module {
                val secret = System.getenv("JWT_SECRET") ?: ""
                val issuer = System.getenv("JWT_ISSUER") ?: ""
                val audience = System.getenv("JWT_AUDIENCE") ?: ""
                val lifetime = System.getenv("JWT_LIFETIME")?.toLong() ?: 2592000000

                single { JwtConfig(secret, issuer, audience, lifetime) }

                single { JwtService(get()) }
            },
            module {
                val database = System.getenv("POSTGRES_DB") ?: ""
                val user = System.getenv("POSTGRES_USER") ?: ""
                val password = System.getenv("POSTGRES_PASSWORD") ?: ""
                val host = System.getenv("POSTGRES_HOST") ?: ""
                val port = System.getenv("POSTGRES_PORT") ?: ""

                single { PostgresConfig(database, user, password, host, port) }

                single { PostgresService(get()) }

            },
            module {
                single { UsersService(get()) }

                single { ChatsService(get()) }

                single { MessagesService(get()) }
            },
            module {
                single { HashingConfig() }

                single { HashingService(get()) }
            },
            module {
                single { RegisterValidator(get()) }
            },
            module {
                single { GptService() }
            }
        )
    }
}
