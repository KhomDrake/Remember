package com.remember.repository

import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.moment.MomentBody
import com.remember.repository.model.moment.toCreateMoment
import com.remember.repository.model.moment.toMomentBodyRequest
import com.remember.repository.operation.asyncLiveData
import com.remember.repository.operation.hasToken

class CreateMomentRepository(val api: RememberApi) {

    fun insertMoment(momentBody: MomentBody) =
        asyncLiveData {
            api.hasToken()
            api.createMomentAsync(
                momentBody.idMemoryLine,
                momentBody.toMomentBodyRequest()
            ).await().toCreateMoment()
        }
}
