package dev.noctis.postgres.table.chats

import dev.noctis.postgres.table.users.Users
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import java.util.UUID
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Chats : Table() {
    val id = uuid("id").clientDefault { UUID.randomUUID() }
    val title = varchar("title", length = 50)
    val userId = uuid("user_id").references(Users.id)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}