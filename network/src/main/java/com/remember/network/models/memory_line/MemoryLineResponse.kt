package com.remember.network.models.memory_line

import com.google.gson.annotations.SerializedName
import com.remember.network.models.profile.AccountImageResponse

class MemoryLinePaginationResponse(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<MemoryLineResponse>
)

class MemoryLineDetailResponse(
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val privacy: String,
    val owner: OwnerResponse,
    @SerializedName("some_participants")
    val participants: List<ParticipantImageResponse>
)

class MemoryLineOwnerResponse(
    val id: String,
    val owner: OwnerResponse
)

class OwnerResponse(
    val id: String,
    val nickname: String,
    val name: String,
    val username: String,
    val photo: String? = null
)

class MemoryLineResponse(
    val title: String,
    val id: String,
    val owner: OwnerResponse,
    val description: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("some_moments")
    val moments: List<MomentMemoryLineResponse>,
    @SerializedName("some_participants")
    val participants: List<ParticipantResponse>,
    @SerializedName("created_at")
    val creationDate: String,
    @SerializedName("updated_at")
    val updatedDate: String
)

class UpdateMemoryLineRequest(
    val title: String
)

class MomentMemoryLineResponse(
    val id: String,
    val file: String
)

class ParticipantImageResponse(
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val photo: String? = null
)

class ParticipantResponse(
    val id: String,
    val participant: AccountImageResponse,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)