package api.v1.auth.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)
