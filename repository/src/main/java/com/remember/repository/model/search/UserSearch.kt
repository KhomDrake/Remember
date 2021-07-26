package com.remember.repository.model.search

import android.os.Parcelable
import com.remember.network.models.search.UserSearchResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserSearch(
    val id: String,
    val username: String,
    val nickname: String,
    val picture: String?
) : Parcelable {
    constructor(userSearchResponse: UserSearchResponse) : this(
        userSearchResponse.id,
        userSearchResponse.username,
        userSearchResponse.nickname,
        userSearchResponse.picture
    )
}
