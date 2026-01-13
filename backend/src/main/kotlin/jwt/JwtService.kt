package dev.noctis.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtService(private val jwtConfig: JwtConfig) {

    fun generateToken(subject: String?): String {
        val algorithm = Algorithm.HMAC256(jwtConfig.secret)

        val token = JWT.create()
            .withIssuer(jwtConfig.issuer)
            .withAudience(jwtConfig.audience)
            .withSubject(subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + jwtConfig.lifetime))
            .sign(algorithm)

        return token
    }
}