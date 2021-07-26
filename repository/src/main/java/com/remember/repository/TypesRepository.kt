package com.remember.repository

import com.remember.network.models.type.CreateMemoryLineTypeDetailRequest
import com.remember.network.models.type.CreateMemoryLineTypeRequest
import com.remember.network.models.type.UpdateMemoryLineTypeRequest
import com.remember.network.retrofit.RememberApi
import com.remember.repository.cache.CacheBox
import com.remember.repository.cache.CacheStrategy
import com.remember.repository.model.MemoryLineType
import com.remember.repository.model.MemoryLineTypePagination
import com.remember.repository.operation.asyncLiveData
import com.remember.repository.operation.hasToken

class TypesRepository(private val rememberApi: RememberApi) {

    fun memoryLineTypes(page: Int = 1) = asyncLiveData(
        CacheBox.key + " $page",
        CacheBox.memoryLineTypesHashMap,
        CacheStrategy.CACHE
    ) {
        rememberApi.hasToken()
        MemoryLineTypePagination(rememberApi.memoryLinesTypesAsync(page).await())
    }

    fun createMemoryLineType(name: String, priority: Int) = asyncLiveData {
        rememberApi.hasToken()
        val response = rememberApi.createMemoryLinesTypesAsync(
            CreateMemoryLineTypeRequest(
                name,
                CreateMemoryLineTypeDetailRequest(priority)
            )
        ).await()
        CacheBox.memoryLineTypesHashMap.clear()
        response
    }

    fun updateMemoryLineTypes(types: List<MemoryLineType>) = asyncLiveData {
        rememberApi.hasToken()
        rememberApi.updateMemoryLinesTypesAsync(types.map {
            UpdateMemoryLineTypeRequest(
                it.idTypeUser,
                it.idType,
                it.priority
            )
        }).await()
        CacheBox.memoryLineTypesHashMap.clear()
    }
}
