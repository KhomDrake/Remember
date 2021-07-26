package com.remember.common.model

import android.os.Parcelable
import com.remember.repository.model.memoryline.MemoryLine
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemoryLineBaseInformation(
    val idMemoryLine: String,
    val ownerId: String,
    var name: String
) : Parcelable

fun MemoryLine.toMemoryLineBaseInformation() =
    MemoryLineBaseInformation(
        id,
        owner.id,
        title
    )