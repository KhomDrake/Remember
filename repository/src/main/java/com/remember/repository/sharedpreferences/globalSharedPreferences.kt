package com.remember.repository.sharedpreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFERENCES_REMEMBER = "Remember"
private const val REFRESH_TOKEN_ASYNC = "refreshTokenAsync"

internal lateinit var sharedPreferences: SharedPreferences

fun Application.setupSharedPreferences() {
    sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_REMEMBER, Context.MODE_PRIVATE)
}

fun removeRefreshToken() {
    sharedPreferences.edit().putString(REFRESH_TOKEN_ASYNC, null).apply()
}

fun addRefreshToken(refreshToken: String?) {
    sharedPreferences.edit().putString(REFRESH_TOKEN_ASYNC, refreshToken).apply()
}

fun getRefreshToken() : String? {
    return sharedPreferences.getString(REFRESH_TOKEN_ASYNC, null)
}
