package com.remember.repository

import com.remember.repository.cache.CacheBox
import com.remember.repository.operation.asyncLiveData
import com.remember.network.models.memory_line.UpdateMemoryLineRequest
import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.memoryline.Owner
import com.remember.repository.model.memoryline.toMemoryLine
import com.remember.repository.model.participant.toParticipantPagination
import com.remember.repository.operation.hasToken

class MemoryLineConfigurationRepository(private val api: RememberApi) {

    fun updateMemoryLine(idMemoryLine: String, name: String) =
        asyncLiveData {
            api.hasToken()
            api.updateMemoryLineNameAsync(idMemoryLine, UpdateMemoryLineRequest(name)).await().toMemoryLine()
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

    fun deleteMemoryLine(idMemoryLine: String) =
        asyncLiveData {
            api.hasToken()
            api.deleteMemoryLineAsync(idMemoryLine).await()
        }
}
