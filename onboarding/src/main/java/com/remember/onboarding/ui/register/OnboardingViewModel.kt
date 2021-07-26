package com.remember.onboarding.ui.register

import androidx.lifecycle.ViewModel
import com.remember.extension.toDateUS
import com.remember.repository.AuthRepository
import com.remember.repository.model.auth.Signup

class OnboardingViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun verifyEmail(email: String) = authRepository.verifyEmail(email)

    fun createAccount(
        username: String,
        email: String,
        password: String,
        nickname: String,
        birthDate: String,
        cellphone: String,
        gender: String,
        name: String
    ) = authRepository.createAccount(
        Signup(
            username = username,
            email = email,
            password = password,
            nickname = nickname,
            birthDate = birthDate.toDateUS(),
            phoneNumber = cellphone,
            gender = gender,
            bio = "",
            name = name
        )
    )
}
