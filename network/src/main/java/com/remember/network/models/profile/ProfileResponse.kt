package com.remember.network.models.profile

import com.google.gson.annotations.SerializedName

class ProfileResponse(
    @SerializedName("id")
    val id: String,
    val name: String,
    val nickname: String,
    @SerializedName("birth_date")
    val birthDate: String,
    val email: String,
    val username: String,
    val photo: String?,
    val bio: String,
    val gender: String,
    @SerializedName("accept_terms")
    val acceptTerms: Boolean
)

class AccountResponse(
    val id: String,
    val photo: String? = null,
    val name: String,
    val nickname: String,
    val username: String
)

class AccountImageResponse(
    val id: String,
    val photo: String? = null
)