package com.remember.repository.module

import com.remember.network.retrofit.MockRetrofitFactory
import com.remember.repository.retrofit.RememberApiDebug
import com.remember.network.retrofit.RememberRetrofit
import com.google.gson.GsonBuilder
import com.remember.repository.EnvConfig
import org.koin.dsl.module

val networkModule = module {
    single { RememberRetrofit.build(EnvConfig.rememberUrl!!) }
    single { MockRetrofitFactory.build(get()) }
    single { GsonBuilder().setLenient().create() }
    single { RememberApiDebug.build(get(), get(), get(), get()) }
}