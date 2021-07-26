package com.remember.repository.model.comment

import com.remember.network.models.comment.CommentsPaginationResponse

class CommentsPagination(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<Comment>
)

fun CommentsPaginationResponse.toCommentsPagination() = CommentsPagination(
    next,
    previous,
    count,
    results.map { Comment(it) }
)