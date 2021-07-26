package com.remember.network.retrofit

import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

object MockRetrofitFactory {
    fun build(retrofit: Retrofit) : MockRetrofit {
        val networkBehavior = NetworkBehavior.create().apply {
            setDelay(1000, TimeUnit.MILLISECONDS)
            setFailurePercent(0)
        }

        return MockRetrofit.Builder(retrofit).networkBehavior(networkBehavior).build()
    }
}