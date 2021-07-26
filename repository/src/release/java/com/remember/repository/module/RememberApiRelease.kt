package com.remember.repository.module

import android.content.Context
import com.google.gson.Gson
import com.remember.network.retrofit.RememberApi
import retrofit2.Retrofit

object RememberApiRelease {
    fun build(retrofit: Retrofit, gson: Gson, context: Context): RememberApi {
        return retrofit.create(RememberApi::class.java)
    }
}