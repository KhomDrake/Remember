package com.remember.profile.ui

import androidx.lifecycle.ViewModel
import com.remember.repository.ProfileRepository
import com.remember.repository.model.profile.Profile
import com.remember.repository.model.profile.UpdateProfileImage

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    private var _picture: String? = null
    private lateinit var _profile: Profile
    private lateinit var id: String

    fun profileId() = id

    fun profile() = profileRepository.informationProfile()

    fun setProfile(profile: Profile) {
        id = profile.id
        _picture = profile.photo
        _profile = profile
    }

    fun removeProfilePicture() =
        profileRepository.updateProfile(_profile.id, UpdateProfileImage(null))
}
