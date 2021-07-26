package com.remember.repository.model.profile

import com.remember.network.models.profile.UpdateProfileRequest

class UpdateProfile(
    val firstName: String,
    val lastName: String
)

fun UpdateProfile.toUpdateProfileRequest() = UpdateProfileRequest(
    firstName,
    lastName
)