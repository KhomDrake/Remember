package com.remember.repository

import com.remember.network.auth.Authentication
import com.remember.repository.cache.CacheBox
import com.remember.repository.operation.asyncLiveData
import com.remember.network.retrofit.RememberApi
import com.remember.repository.model.profile.*
import com.remember.repository.operation.hasToken

class ProfileRepository(val api: RememberApi) {

    fun informationProfile() = asyncLiveData(
        CacheBox.key,
        CacheBox.profileHashMap
    ) {
        api.hasToken()
        val profile = api.profileAsync().await().toProfile()
        Authentication.setUserId(profile.id)
        profile
    }

    fun updateProfile(id: String, updateProfileWithImage: UpdateProfileImage) =
        asyncLiveData {
            api.updateProfileWithImage(id, updateProfileWithImage.toUpdateProfileWithImageRequest()).await().toProfile()
        }
}
