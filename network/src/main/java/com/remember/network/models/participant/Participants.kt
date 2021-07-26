package com.remember.network.models.participant

import com.google.gson.annotations.SerializedName
import com.remember.network.models.profile.AccountResponse
import com.remember.network.models.search.UserSearchResponse

class ParticipantPaginationResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ParticipantMemoryLineResponse>
)

class ParticipantMemoryLineResponse(
    @SerializedName("id")
    val idParticipant: String,
    @SerializedName("participant")
    val participant: AccountResponse,
    val owner: Boolean = false,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

class ParticipantSearchResponse(
    val id: String,
    val username: String,
    val nickname: String,
    val photo: String?
)

fun ParticipantSearchResponse.toUserSearch() = UserSearchResponse(
    id,
    username,
    nickname,
    photo
)

fun ParticipantMemoryLineResponse.toUserSearch() = UserSearchResponse(
    idParticipant,
    "",
    participant.username,
    participant.photo
)
