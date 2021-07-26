package com.remember.repository.model.auth

import com.remember.network.models.auth.SigninRequest

class Signin(
    val username: String,
    val password: String
)

fun Signin.toSigninRequest() = SigninRequest(username, password)