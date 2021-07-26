package com.remember.network.models.terms

import com.google.gson.annotations.SerializedName

class TermResponse(
    val title: String,
    val items: List<TermItemResponse>
)

class TermItemResponse(
    val title: String?,
    @SerializedName("sub_title")
    val subTitle: String?,
    val description: String?,
    val type: String,
    val items: List<TermItemResponse>
)