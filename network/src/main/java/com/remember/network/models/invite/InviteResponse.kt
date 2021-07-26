package com.remember.network.models.invite

import com.google.gson.annotations.SerializedName
import com.remember.network.models.profile.AccountResponse

class InvitesPaginationResponse(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<InviteResponse>
)

class CreateInviteRequest(
    val guest: String,
    @SerializedName("memory_line")
    val memoryLine: String
)

class CreateInviteAnswerResponse(
    val guest: String,
    @SerializedName("memory_line")
    val memoryLine: String
)

class InviteResponse(
    val id: String,
    val answered: Boolean,
    val accepted: Boolean,
    @SerializedName("memory_line")
    val memoryLine: MemoryLineInviteResponse,
    val owner: AccountResponse,
    val guest: String
)

class MemoryLineInviteResponse(
    val id: String,
    val title: String,
    val description: String
)

class AnswerInviteRequest(
    val accepted: Boolean
)

class AnswerInviteResponse(
    val accepted: Boolean
)