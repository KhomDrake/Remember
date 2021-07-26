package com.remember.network.models.auth

import com.google.gson.annotations.SerializedName

class VerifyUsernameRequest(
    val username: String
)

class VerifyEmailRequest(
    val email: String
)

class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    @SerializedName("birth_date")
    val birthDate: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val name: String,
    val nickname: String,
    val bio: String = "",
    val gender: String,
    val photo: String? = ""
)

class SignupResponse(
    val id: String,
    val photo: String?,
    val username: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    val bio: String,
    val gender: String,
    @SerializedName("birth_date")
    val birthDate: String
)