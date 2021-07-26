package com.remember.repository.model.auth

import com.remember.network.models.auth.SignupRequest

class Signup(
    val username: String,
    val email: String,
    val password: String,
    val birthDate: String,
    val phoneNumber: String,
    val gender: String,
    val name: String,
    val nickname: String,
    val photo: String? = "",
    val bio: String = ""
)

fun Signup.toSignupRequest() = SignupRequest(
    username, email, password, birthDate, phoneNumber, name, nickname, bio, gender, photo
)