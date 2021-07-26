package com.remember.repository

import com.remember.repository.cache.CacheBox
import com.remember.repository.operation.asyncLiveData
import com.remember.network.models.invite.AnswerInviteRequest
import com.remember.network.models.invite.CreateInviteRequest
import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.invite.toAnswerInvite
import com.remember.repository.operation.hasToken

class InviteRepository(private val api: RememberApi) {

    fun createInvites(idMemoryLine: String, idGuests: List<String>) =
        asyncLiveData {
            api.hasToken()
            api.createInviteManyAsync(idGuests.map {
                CreateInviteRequest(it, idMemoryLine)
            }).await()
        }

    fun answerInvite(idInvite: String, answer: Boolean) =
        asyncLiveData {
            api.hasToken()
            val invite = api.respondInviteAsync(idInvite, AnswerInviteRequest(answer)).await().toAnswerInvite()
            if(answer) {
                CacheBox.memoryLineTypesHashMap.clear()
            }
            invite
        }
}
