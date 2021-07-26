package com.remember.repository

import com.remember.repository.cache.CacheBox
import com.remember.repository.cache.CacheStrategy
import com.remember.repository.operation.asyncLiveData
import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.memoryline.Owner
import com.remember.repository.model.memoryline.toMemoryLinePagination
import com.remember.repository.model.participant.toParticipantPagination
import com.remember.repository.operation.hasToken

class MemoryLineRepository(private val api: RememberApi) {
    fun memoryLine(typeMemoryLine: String, page: Int) =
        asyncLiveData(
            "${CacheBox.key} $typeMemoryLine $page",
            CacheBox.memoryLineHashMap,
            CacheStrategy.UPDATE
        ) {
            api.hasToken()
            api.memoryLineByTag(
                typeMemoryLine,
                page
            ).await().toMemoryLinePagination()
        }

    fun memoryLineParticipants(idMemoryLine: String) =
        asyncLiveData(
            CacheBox.key + idMemoryLine,
            CacheBox.memoryLineParticipantHashMap
        ) {
            api.hasToken()
            val owner = Owner(api.ownerMemoryLine(idMemoryLine).await().owner)
            api.participantsMemoryLine(idMemoryLine).await().toParticipantPagination(owner)
        }
}
