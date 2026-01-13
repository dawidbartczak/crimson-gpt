package dev.noctis.postgres.table.users

import org.jetbrains.exposed.sql.Table
import java.util.UUID

object Users : Table() {
    val id = uuid("id").clientDefault { UUID.randomUUID() }
    val email = varchar("email", length = 255).uniqueIndex()
    val username = varchar("username", length = 30)
    val salt = char("salt", length = 64)
    val hash = char("hash", length = 64)

    override val primaryKey = PrimaryKey(id)
}