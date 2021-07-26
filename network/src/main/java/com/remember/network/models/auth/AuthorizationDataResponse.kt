package com.remember.network.models.auth

import com.google.gson.annotations.SerializedName

class AuthorizationDataResponse(
    @SerializedName("access")
    val access: String,
    @SerializedName("refresh")
    val refresh: String? = null
)
