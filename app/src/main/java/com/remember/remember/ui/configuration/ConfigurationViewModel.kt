package com.remember.remember.ui.configuration

import androidx.lifecycle.ViewModel
import com.remember.repository.AuthRepository

class ConfigurationViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun logout() = authRepository.logout()

}
