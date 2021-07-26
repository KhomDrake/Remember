package com.remember.repository

import com.remember.network.models.invite.CreateInviteRequest
import com.remember.repository.operation.asyncLiveData
import com.remember.network.models.memory_line.CreateMemoryLineRequest
import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.memoryline.toMemoryLine
import com.remember.repository.operation.hasToken
import java.lang.IllegalStateException

class CreateMemoryLineRepository(private val api: RememberApi) {

    fun createMemoryLine(name: String, type: String, guests: List<String>) =
        asyncLiveData {
            api.hasToken()

            if(type.isEmpty()) throw IllegalStateException("Type cannot be empty")

            val createMemoryLine = CreateMemoryLineRequest(
                name,
                type
            )
            val memoryLine = api.createMemoryLineAsync(createMemoryLine).await().toMemoryLine()
            try {
                if(guests.isNotEmpty()) {
                    api.createInviteManyAsync(guests.map {
                        CreateInviteRequest(it, memoryLine.id)
                    }).await()
                }
            } catch (e: Exception) {

            }
            memoryLine
        }
}
