package com.remember.network.models.search

import com.google.gson.annotations.SerializedName

class SearchPaginationResponse(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<UserSearchResponse>
)

class UserSearchResponse(
    @SerializedName("id")
    val id: String,
    val nickname: String,
    val username: String,
    @SerializedName("photo")
    val picture: String?
)
