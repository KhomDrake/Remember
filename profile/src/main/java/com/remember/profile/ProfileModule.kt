package com.remember.profile

import com.remember.common.initializer.Modules
import com.remember.profile.ui.ProfileViewModel
import com.remember.profile.ui.picture.preview.ChangeProfilePictureViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class ProfileModule : Modules() {
    override fun loadModule() = listOf(module {
        viewModel { ProfileViewModel(get()) }
        viewModel { ChangeProfilePictureViewModel(get()) }
    })
}
