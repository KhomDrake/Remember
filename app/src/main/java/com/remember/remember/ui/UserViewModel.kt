package com.remember.remember.ui

import androidx.lifecycle.ViewModel
import com.remember.repository.AuthRepository

class UserViewModel(private val authRepository: AuthRepository): ViewModel() {

    fun removeAuthData() = authRepository.removeAuthData()

    fun setup() = authRepository.setup()
}
