package com.remember.profile.ui.picture.preview

import androidx.lifecycle.ViewModel
import com.remember.extension.toBase64
import com.remember.repository.ProfileRepository
import com.remember.repository.model.profile.UpdateProfileImage
import java.io.File

class ChangeProfilePictureViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    fun changeProfilePicture(id: String, file: File) =
        profileRepository.updateProfile(id, UpdateProfileImage(file.toBase64()))

}
