package com.remember.repository.module

import com.remember.network.retrofit.RememberRetrofit
import com.google.gson.GsonBuilder
import com.remember.repository.BuildConfig
import org.koin.dsl.module

val networkModule = module {
    single { RememberRetrofit.build(BuildConfig.BASE_URL) }
    single { GsonBuilder().setLenient().create() }
    single { RememberApiRelease.build(get(), get(), get()) }
}