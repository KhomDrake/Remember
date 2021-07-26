package com.remember.repository

import com.remember.network.retrofit.RememberApi
import com.remember.repository.cache.CacheBox
import com.remember.repository.cache.CacheStrategy
import com.remember.repository.model.moment.Moment
import com.remember.repository.model.participant.ParticipantFull
import com.remember.repository.operation.asyncLiveData
import com.remember.repository.operation.hasToken

class HistoryRepository(private val rememberApi: RememberApi) {
    fun momentsHistory() = asyncLiveData(
        CacheBox.key,
        CacheBox.memoryLineHistory,
        CacheStrategy.CACHE
    ) {
        rememberApi.hasToken()
        rememberApi.memoryLineHistoryAsync().await().map {
            Moment(it)
        }
    }

    fun participantsHistory() = asyncLiveData {
        rememberApi.memoryLineParticipantsHistoryAsync().await().map {
            ParticipantFull(it)
        }
    }
}
