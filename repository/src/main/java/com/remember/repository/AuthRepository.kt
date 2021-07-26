package com.remember.repository

import com.remember.network.auth.Authentication
import com.remember.network.models.auth.RefreshTokenRequest
import com.remember.network.models.auth.VerifyEmailRequest
import com.remember.network.models.auth.VerifyUsernameRequest
import com.remember.repository.operation.asyncLiveData
import com.remember.network.retrofit.RememberApi
import com.remember.repository.cache.CacheBox
import com.remember.repository.model.auth.AuthorizationData
import com.remember.repository.model.auth.RefreshToken
import com.remember.repository.model.auth.Signin
import com.remember.repository.model.auth.Signup
import com.remember.repository.model.auth.toAuthorizationData
import com.remember.repository.model.auth.toRefreshTokenRequest
import com.remember.repository.model.auth.toSigninRequest
import com.remember.repository.model.auth.toSignupRequest
import com.remember.repository.push.InformationDevice
import com.remember.repository.sharedpreferences.addRefreshToken
import com.remember.repository.sharedpreferences.getRefreshToken
import com.remember.repository.sharedpreferences.removeRefreshToken
import kotlin.Exception

class InvalidUsername: Exception()

class AuthRepository(private val api: RememberApi) {

    fun userId() = Authentication.userId

    fun login(body: Signin) = asyncLiveData {
        val auth = api.signinAsync(body.toSigninRequest()).await().toAuthorizationData()
        setupInformation(auth)
    }

    fun createAccount(body: Signup) = asyncLiveData {
        val username = body.username
        runCatching { api.verifyUsername(VerifyUsernameRequest(username)).await() }.getOrNull()
            ?: throw InvalidUsername()

        api.signupAsync(body.toSignupRequest()).await()
    }

    fun refreshToken(body: RefreshToken) =
        asyncLiveData {
            val authData = api.refreshTokenAsync(body.toRefreshTokenRequest()).await().toAuthorizationData()
            setToken(authData.access)
            addAuthData(authData.refresh)
            authData
        }

    fun logout() = asyncLiveData {
        removeRefreshToken()
        Authentication.reset()
        CacheBox.resetAllCache()
        true
    }

    fun removeAuthData() = asyncLiveData {
        removeRefreshToken()
        true
    }

    private fun addAuthData(refreshToken: String?) =
        addRefreshToken(
            refreshToken
        )

    private fun setToken(token: String) {
        Authentication.setToken(token)
    }

    fun hasAuthData() = asyncLiveData {
        val refreshToken =
            getRefreshToken()
        Pair(refreshToken, refreshToken.isNullOrEmpty().not())
    }

    fun verifyEmail(email: String) =
        asyncLiveData {
            api.verifyEmail(VerifyEmailRequest(email)).await()
        }

    private suspend fun setupInformation(authorizationData: AuthorizationData) : Boolean {
        setToken(authorizationData.access)
        addAuthData(authorizationData.refresh)
        addDevice()
        return try {
            val profile = api.profileAsync().await()
            profile.acceptTerms
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun addDevice() {
        try {
            api.addDeviceAsync(InformationDevice.informationDevice()).await()
        } catch (e: Exception) { }
    }

    fun setup() = asyncLiveData {
        val refreshToken =
            getRefreshToken()
        if (refreshToken != null) {
            val authorizationData = api.refreshTokenAsync(RefreshTokenRequest(refreshToken)).await().toAuthorizationData()
            setupInformation(authorizationData)
        } else throw IllegalStateException("Don't have refresh token")
    }
}
