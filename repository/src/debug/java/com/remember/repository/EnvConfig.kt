package com.remember.repository

import android.content.Context
import java.lang.ref.WeakReference
import org.koin.core.KoinComponent
import org.koin.core.get

object EnvConfig : KoinComponent {

    const val DEV = BuildConfig.BASE_URL
    const val MOCK = "https://127.0.0.1/"
    private const val URL_KEY = "remember_url_env"
    private val context = WeakReference(get<Context>())
    private val prefs = context.get()?.getSharedPreferences("Remember", Context.MODE_PRIVATE)

    var rememberUrl: String? = null
        get() = prefs?.getString(URL_KEY, MOCK)

        set(value) {
            value?.apply {
                prefs?.edit()?.putString(URL_KEY, this)?.apply()
            }
            field = value
        }

    val isMock: Boolean
        get() = rememberUrl == MOCK
}
