package dev.noctis.postgres.table.chats

import dev.noctis.postgres.PostgresService
import dev.noctis.postgres.table.users.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

class ChatsService(postgresService: PostgresService) {

    init {
        val database = postgresService.getDatabase()

        transaction(database) {
            SchemaUtils.create(Chats)
        }
    }

    suspend fun insert(chat: Chat) = dbQuery {
        Chats.insert {
            it[title] = chat.title
            it[userId] = chat.userId
        }[Chats.id]
    }

    suspend fun selectAllByUserId(userId: UUID): List<Chat> = dbQuery {
        Chats.selectAll()
            .where { Chats.userId eq userId }
            .orderBy(Chats.createdAt, SortOrder.DESC)
            .map {
                Chat(
                    title = it[Chats.title],
                    userId = it[Chats.userId],
                    createdAt = it[Chats.createdAt],
                    id = it[Chats.id]
                )
            }
    }

    suspend fun selectByUserIdAndChatId(userId: UUID, chatId: UUID) = dbQuery {
        Chats
            .join(Users, JoinType.INNER, onColumn = Chats.userId, otherColumn = Users.id)
            .selectAll()
            .where { (Chats.id eq chatId) and (Users.id eq userId) }
            .map {
                Chat(
                    title = it[Chats.title],
                    userId = it[Chats.userId],
                    createdAt = it[Chats.createdAt],
                    id = it[Chats.id]
                )
            }
            .firstOrNull()
    }

    suspend fun updateTitleByChatId(chatId: UUID, title: String) = dbQuery {
        Chats.update({ (Chats.id eq chatId) }) {
            it[Chats.title] = title
        }
    }

    suspend fun deleteByChatId(chatId: UUID) = dbQuery {
        Chats.deleteWhere { (id eq chatId) }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}