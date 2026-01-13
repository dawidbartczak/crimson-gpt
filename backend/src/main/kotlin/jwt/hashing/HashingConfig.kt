package dev.noctis.jwt.hashing

data class HashingConfig(
    val secureRandom: String = "DRBG",
    val messageDigest: String = "SHA-256",
)