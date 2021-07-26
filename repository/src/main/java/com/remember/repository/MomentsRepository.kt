package com.remember.repository

import com.remember.repository.cache.CacheBox
import com.remember.repository.operation.asyncLiveData
import com.remember.network.retrofit.RememberApi
import com.remember.repository.cache.CacheStrategy
import com.remember.repository.model.memoryline.toMemoryLineDetail
import com.remember.repository.model.moment.toMomentsPagination
import com.remember.repository.operation.hasToken

class MomentsRepository(private val api: RememberApi) {

    fun momentsPagination(idMemoryLine: String, page: Int) =
        asyncLiveData(
            "${CacheBox.key}$idMemoryLine$page",
            CacheBox.momentsHashMap,
            CacheStrategy.CACHE
        ) {
            api.hasToken()
            api.momentsPagination(idMemoryLine, page).await().toMomentsPagination()
        }

    fun detailMemoryLine(idMemoryLine: String) =
        asyncLiveData(
            CacheBox.key + idMemoryLine,
            CacheBox.memoryLineDetailHashMap
        ) {
            api.hasToken()
            api.detailMemoryline(idMemoryLine).await().toMemoryLineDetail()
        }
}
