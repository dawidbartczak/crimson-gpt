package dev.noctis.jwt.hashing

fun String.toHex(): String = this.toByteArray()
    .joinToString("") { "%02x".format(it) }

fun String.toByteArrayFromHex(): ByteArray = this.chunked(2)
    .map { it.toInt(16).toByte() }
    .toByteArray()