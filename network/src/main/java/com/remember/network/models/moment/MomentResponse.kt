package com.remember.network.models.moment

import com.google.gson.annotations.SerializedName
import com.remember.network.models.profile.AccountImageResponse

class MomentsPaginationResponse(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<MomentResponse>
)

class MomentResponse(
    @SerializedName("id")
    val idMoment: String,
    val owner: AccountImageResponse,
    val description: String?,
    @SerializedName("memory_line")
    val idMemoryLine: String,
    @SerializedName("file")
    val urlBucket: String,
    @SerializedName("comments_count")
    val commentsCount: Int,
    @SerializedName("pub_date")
    val creationDate: String,
    @SerializedName("updated_at")
    val updateAt: String
)
