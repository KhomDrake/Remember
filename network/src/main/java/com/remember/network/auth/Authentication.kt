package com.remember.network.auth

object Authentication {
    private var token: String = "asd"
    private var _userId: String? = null

    val userId: String?
        get() = _userId

    fun getToken() = "Bearer $token"

    fun setUserId(id: String) {
        _userId = id
    }

    fun tokenIsEmpty() = token.isEmpty()

    fun setToken(token: String) {
        Authentication.token = token
    }

    fun reset() {
        token = ""
        _userId = null
    }
}
