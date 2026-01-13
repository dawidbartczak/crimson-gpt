package dev.noctis.jwt.hashing

import java.security.MessageDigest
import java.security.SecureRandom

@OptIn(ExperimentalStdlibApi::class)
class HashingService(private val hashingConfig: HashingConfig) {

    fun generateSalt(): String {
        val random = SecureRandom.getInstance(hashingConfig.secureRandom)

        val salt = ByteArray(32)

        random.nextBytes(salt)

        return salt.toHexString()
    }

    fun generateHash(password: String, salt: String): String {
        val digest = MessageDigest.getInstance(hashingConfig.messageDigest)

        val passwordBytes = password.toHex().toByteArrayFromHex()
        val saltBytes = salt.toByteArrayFromHex()
        val combinedBytes = passwordBytes + saltBytes

        val hashedBytes = digest.digest(combinedBytes)

        return hashedBytes.toHexString()
    }

    fun verify(password: String, salt: String, hash: String): Boolean {
        val generatedHash = generateHash(password, salt)

        return generatedHash == hash
    }
}