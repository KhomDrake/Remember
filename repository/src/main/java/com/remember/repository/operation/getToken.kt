package com.remember.repository.operation

import com.remember.network.auth.Authentication
import com.remember.network.models.auth.RefreshTokenRequest
import com.remember.network.retrofit.RememberApi
import com.remember.repository.sharedpreferences.addRefreshToken
import com.remember.repository.sharedpreferences.getRefreshToken

suspend fun RememberApi.hasToken() {
    if (Authentication.tokenIsEmpty())  {
        val refreshTokenRequest = refreshTokenAsync(RefreshTokenRequest(getRefreshToken() ?: return)).await()
        Authentication.setToken(refreshTokenRequest.access)
        addRefreshToken(refreshTokenRequest.refresh)
    }
}