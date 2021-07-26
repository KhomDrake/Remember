package com.remember.repository.retrofit

import android.content.Context
import com.google.gson.Gson
import com.remember.network.retrofit.RememberApi
import com.remember.network.retrofit.RememberApiMock
import com.remember.repository.EnvConfig
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit

object RememberApiDebug {
    fun build(retrofit: Retrofit, mockRetrofit: MockRetrofit, gson: Gson, context: Context): RememberApi {
        return if(EnvConfig.isMock)
            RememberApiMock(
                mockRetrofit,
                gson,
                context
            ) else retrofit.create(RememberApi::class.java)
    }
}