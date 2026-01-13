package dev.noctis.postgres.util

import java.util.UUID

fun String.toUUID(): UUID = UUID.fromString(this)
