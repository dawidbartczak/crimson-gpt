package dev.noctis.postgres.table.users

import dev.noctis.postgres.PostgresService
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UsersService(postgresService: PostgresService) {

    init {
        val database = postgresService.getDatabase()

        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    suspend fun create(user: User) = dbQuery {
        Users.insert {
            it[email] = user.email
            it[username] = user.username
            it[salt] = user.salt
            it[hash] = user.hash
        }
    }

    suspend fun readByUserId(userId: UUID): User? = dbQuery {
        Users.selectAll()
            .where { Users.id eq userId }
            .map {
                User(
                    id = it[Users.id],
                    email = it[Users.email],
                    username = it[Users.username],
                    salt = it[Users.salt],
                    hash = it[Users.hash]
                )
            }
            .firstOrNull()
    }

    suspend fun readByEmail(email: String): User? = dbQuery {
        Users.selectAll()
            .where { Users.email eq email }
            .map {
                User(
                    id = it[Users.id],
                    email = it[Users.email],
                    username = it[Users.username],
                    salt = it[Users.salt],
                    hash = it[Users.hash]
                )
            }
            .firstOrNull()
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}