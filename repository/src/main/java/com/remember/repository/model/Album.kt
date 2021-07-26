package com.remember.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Album(
    val name: String?,
    val id: String?,
    val uriFirstImage: String?,
    val images: String
) : Parcelable