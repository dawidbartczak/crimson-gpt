package dev.noctis.postgres.table.messages

import dev.noctis.postgres.table.chats.Chats
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

object Messages : Table() {
    val id = uuid("id").clientDefault { UUID.randomUUID() }
    val content = text("content")
    val sender = integer("sender")
    val chatId = uuid("chat_id").references(Chats.id, onDelete = ReferenceOption.CASCADE)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}