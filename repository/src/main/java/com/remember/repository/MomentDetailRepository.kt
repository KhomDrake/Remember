package com.remember.repository

import com.remember.repository.cache.CacheBox
import com.remember.repository.operation.asyncLiveData
import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.moment.toMoment
import com.remember.repository.operation.hasToken

class MomentDetailRepository(private val api: RememberApi) {

    fun momentDetail(idMoment: String) =
        asyncLiveData(
            CacheBox.key + idMoment,
            CacheBox.momentDetailHashMap
        ) {
            api.hasToken()
            api.momentDetailAsync(idMoment).await().toMoment()
        }

    fun deleteMoment(idMoment: String) =
        asyncLiveData {
            api.hasToken()
            api.deleteMomentAsync(idMoment).await()
        }
}
