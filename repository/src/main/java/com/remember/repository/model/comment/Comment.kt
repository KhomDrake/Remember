package com.remember.repository.model.comment

import com.remember.network.models.comment.CommentResponse
import com.remember.repository.model.memoryline.Owner
import com.remember.repository.model.pagination.DataPagination

class Comment(
    val idComment: String,
    val content: String,
    val owner: Owner,
    val createdAt: String,
    val updatedAt: String
) : DataPagination(idComment) {
    constructor(commentResponse: CommentResponse) : this(
        commentResponse.idComment,
        commentResponse.content,
        Owner(commentResponse.owner),
        commentResponse.createdAt,
        commentResponse.updatedAt
    )
}

fun CommentResponse.toComment() = Comment(
    idComment,
    content,
    Owner(owner),
    createdAt,
    updatedAt
)