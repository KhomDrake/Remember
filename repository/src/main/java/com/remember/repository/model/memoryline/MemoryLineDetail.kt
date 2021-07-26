package com.remember.repository.model.memoryline

import android.os.Parcelable
import com.remember.network.models.memory_line.MemoryLineDetailResponse
import com.remember.network.models.memory_line.OwnerResponse
import com.remember.repository.model.participant.ParticipantImage
import kotlinx.android.parcel.Parcelize

class MemoryLineDetail(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val privacy: String,
    val owner: Owner,
    val participants: List<ParticipantImage>
)

fun MemoryLineDetailResponse.toMemoryLineDetail() = MemoryLineDetail(
    id,
    title,
    description,
    createdAt,
    updatedAt,
    privacy,
    Owner(owner),
    participants.map { ParticipantImage(it) }
)

@Parcelize
class Owner(
    val id: String,
    val nickname: String,
    val name: String,
    val username: String,
    val photo: String? = null
) : Parcelable {
    constructor(owner: OwnerResponse) : this(
        owner.id,
        owner.nickname,
        owner.name,
        owner.username,
        owner.photo
    )
}