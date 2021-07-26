package com.remember.repository

import com.remember.repository.operation.asyncLiveData
import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.search.toSearchPagination
import com.remember.repository.operation.hasToken

class SearchRepository(private val api: RememberApi) {

    fun searchProfile(username: String) =
        asyncLiveData {
            api.hasToken()
            api.searchProfile(username).await().toSearchPagination()
        }
}
