package com.remember.network.models.moment

import com.google.gson.annotations.SerializedName
import com.remember.network.models.profile.AccountImageResponse

class MomentBodyRequest(
    @SerializedName("file")
    val moment: String,
    val description: String
)

class CreateMomentResponse(
    val id: String,
    val file: String,
    val description: String,
    val owner: AccountImageResponse,
    @SerializedName("memory_line")
    val idMemoryLine: String,
    @SerializedName("pub_date")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)