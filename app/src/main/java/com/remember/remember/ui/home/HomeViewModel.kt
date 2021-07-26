package com.remember.remember.ui.home

import androidx.lifecycle.ViewModel
import com.remember.repository.ProfileRepository
import com.remember.repository.TypesRepository

class HomeViewModel(
    private val typesRepository: TypesRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    fun types() = typesRepository.memoryLineTypes()

    fun profile() = profileRepository.informationProfile()
}
