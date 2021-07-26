package com.remember.repository

import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.notification.DefaultNotification
import com.remember.repository.operation.asyncLiveData
import com.remember.repository.operation.hasToken

class NotificationRepository(private val rememberApi: RememberApi) {

    fun notification() = asyncLiveData {
        rememberApi.hasToken()
        DefaultNotification(rememberApi.notificationsAsync().await())
    }
}
