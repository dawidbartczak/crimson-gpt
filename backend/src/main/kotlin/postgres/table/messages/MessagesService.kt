package dev.noctis.postgres.table.messages

import dev.noctis.postgres.PostgresService
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class MessagesService(postgresService: PostgresService) {

    init {
        val database = postgresService.getDatabase()

        transaction(database) {
            SchemaUtils.create(Messages)
        }
    }

    suspend fun insert(message: Message) = dbQuery {
        Messages.insert {
            it[content] = message.content
            it[sender] = message.author
            it[chatId] = message.chatId
        }
    }

    suspend fun selectAllByChatId(chatId: UUID) = dbQuery {
        Messages
            .selectAll()
            .where { Messages.chatId eq chatId }
            .orderBy(Messages.createdAt)
            .map { it ->
                Message(
                    id = it[Messages.id],
                    content = it[Messages.content],
                    author = it[Messages.sender],
                    createdAt = it[Messages.createdAt],
                    chatId = it[Messages.chatId]
                )
            }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}