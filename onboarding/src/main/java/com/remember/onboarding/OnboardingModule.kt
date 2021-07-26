package com.remember.onboarding

import com.remember.common.initializer.Modules
import com.remember.onboarding.ui.login.LoginViewModel
import com.remember.onboarding.ui.register.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class OnboardingModule : Modules() {
    override fun loadModule() = listOf(module {
        viewModel { OnboardingViewModel(get()) }
        viewModel { LoginViewModel(get()) }
    })
}
