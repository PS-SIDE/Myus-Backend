package io.paku.myus.api.auth.dto.response

data class AuthDto(
    val accessToken: String,
    val refreshToken: String
)