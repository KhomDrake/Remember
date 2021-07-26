package com.remember.repository.model.auth

import com.remember.network.models.auth.RefreshTokenRequest

class RefreshToken(
    val refreshToken: String
)

fun RefreshToken.toRefreshTokenRequest() = RefreshTokenRequest(refreshToken)