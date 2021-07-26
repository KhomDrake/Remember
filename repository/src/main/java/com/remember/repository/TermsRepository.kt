package com.remember.repository

import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.terms.Term
import com.remember.repository.model.terms.TermItem
import com.remember.repository.operation.asyncLiveData

class TermsRepository(private val rememberApi: RememberApi) {

    fun terms() = asyncLiveData {
        val termResponse = rememberApi.termsAsync().await()
        Term(termResponse.title, termResponse.items.map { TermItem(it) })
    }

    fun acceptTerms() = asyncLiveData {
        rememberApi.acceptTermsAsync().await()
    }
}
