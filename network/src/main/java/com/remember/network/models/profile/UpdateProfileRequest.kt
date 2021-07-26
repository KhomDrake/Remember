package com.remember.network.models.profile

import com.google.gson.annotations.SerializedName

class UpdateProfileRequest(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String
)

class UpdateProfileImageRequest(
    @SerializedName("photo")
    val picture: String?
)
