package com.remember.network.models.notification

import com.google.gson.annotations.SerializedName
import com.remember.network.models.invite.InviteResponse

class DefaultNotificationsResponse(
    val invites: List<InviteResponse>,
    val memories: List<MemoriesResponse>,
    @SerializedName("memory_line_activities")
    val memoryLineActivities: List<MemoryLineActivityResponse>
)

class MemoriesResponse(
    val id: String
)

class MemoryLineActivityResponse(
    val id: String
)