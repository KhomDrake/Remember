package com.remember.network.retrofit

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        return when {
            response.code() == 204 -> emptyResponse(response)
            response.isSuccessful &&
                    (request.url().url().toString().endsWith("verify_username/") ||
                            request.url().url().toString().endsWith("verify_email/")) -> emptyResponse(response)
            else -> response
        }
    }

    private fun emptyResponse(response: Response) = response.newBuilder().apply {
        code(200)
        body(ResponseBody.create(MediaType.get("application/json"), "true"))
    }.build()
}