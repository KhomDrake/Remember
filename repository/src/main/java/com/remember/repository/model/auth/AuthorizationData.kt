package com.remember.repository.model.auth

import com.remember.network.models.auth.AuthorizationDataResponse

class AuthorizationData(
    val access: String,
    val refresh: String? = null
)

fun AuthorizationDataResponse.toAuthorizationData() = AuthorizationData(access, refresh)