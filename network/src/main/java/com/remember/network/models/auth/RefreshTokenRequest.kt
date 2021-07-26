package com.remember.network.models.auth

import com.google.gson.annotations.SerializedName

class RefreshTokenRequest(
    @SerializedName("refresh")
    val refreshToken: String
)
