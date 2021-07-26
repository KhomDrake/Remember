package com.remember.repository.model.profile

import com.remember.network.models.profile.UpdateProfileImageRequest

class UpdateProfileImage(
    val picture: String?
)

fun UpdateProfileImage.toUpdateProfileWithImageRequest() = UpdateProfileImageRequest(
    picture
)