package com.remember.network.models.auth

class ConfirmSignupRequest(
    val username: String,
    val password: String,
    val code: String
)
