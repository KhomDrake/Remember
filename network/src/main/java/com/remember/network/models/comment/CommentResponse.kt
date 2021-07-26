package com.remember.network.models.comment

import com.google.gson.annotations.SerializedName
import com.remember.network.models.memory_line.OwnerResponse

class CommentsPaginationResponse(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<CommentResponse>
)

class CommentResponse(
    @SerializedName("id")
    val idComment: String,
    val content: String,
    val owner: OwnerResponse,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

class CreateCommentRequest(
    val content: String
)
