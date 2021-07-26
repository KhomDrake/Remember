package com.remember.network.retrofit

import com.remember.network.auth.Authentication
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if(request.url().url().toString().contains("/api/v1/history/", ignoreCase = true).not()) {
            return chain.proceed(request.newBuilder().addHeader("Authorization", Authentication.getToken())
                .build())
        }

        return chain.proceed(request)
    }
}