package com.remember.repository

import com.remember.repository.operation.asyncLiveData
import com.remember.network.models.comment.CreateCommentRequest
import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.comment.toComment
import com.remember.repository.model.comment.toCommentsPagination
import com.remember.repository.operation.hasToken

class MomentCommentsRepository(private val api: RememberApi) {

    fun commentsMoment(idMoment: String, page: Int) =
        asyncLiveData {
            api.hasToken()
            api.commentsMomentAsync(idMoment, page).await().toCommentsPagination()
        }

    fun insertComment(idMoment: String, comment: String) =
        asyncLiveData {
            api.hasToken()
            api.insertCommentMomentAsync(
                idMoment,
                CreateCommentRequest(comment)
            ).await().toComment()
        }
}
