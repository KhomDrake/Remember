package com.remember.onboarding.ui.login

import androidx.lifecycle.ViewModel
import com.remember.repository.AuthRepository
import com.remember.repository.model.auth.Signin

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private var password: String = ""
    private var usernameOrEmail: String = ""

    fun setPassword(value: String) {
        password = value
    }

    fun setup() = authRepository.setup()

    fun setUsernameOrEmail(value: String) {
        usernameOrEmail = value
    }

    fun canProceed() = usernameOrEmail.isNotEmpty() && password.isNotEmpty()

    fun login() = authRepository.login(Signin(usernameOrEmail, password))

}
