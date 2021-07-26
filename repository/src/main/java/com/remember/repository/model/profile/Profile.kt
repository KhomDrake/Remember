package com.remember.repository.model.profile

import com.remember.network.models.profile.ProfileResponse

class Profile(
    val id: String,
    val name: String,
    val nickname: String,
    val birthDate: String,
    val email: String,
    val username: String,
    val photo: String?,
    val bio: String,
    val gender: String
)

fun ProfileResponse.toProfile() = Profile(
    id,
    name,
    nickname,
    birthDate,
    email,
    username,
    photo,
    bio,
    gender
)