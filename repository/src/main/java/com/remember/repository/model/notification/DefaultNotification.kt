package com.remember.repository.model.notification

import com.remember.network.models.notification.DefaultNotificationsResponse
import com.remember.network.models.notification.MemoriesResponse
import com.remember.network.models.notification.MemoryLineActivityResponse
import com.remember.repository.model.invite.Invite

class DefaultNotification(
    val invites: List<Invite>,
    val memories: List<Memories>,
    val memoryLineActivities: List<MemoryLineActivity>
) {
    constructor(defaultNotificationsResponse: DefaultNotificationsResponse): this(
        defaultNotificationsResponse.invites.map { Invite(it) }.filter { !it.answered },
        defaultNotificationsResponse.memories.map { Memories(it) },
        defaultNotificationsResponse.memoryLineActivities.map { MemoryLineActivity(it) }
    )
}

class Memories(
    val id: String
) {
    constructor(memoriesResponse: MemoriesResponse): this(memoriesResponse.id)
}

class MemoryLineActivity(
    val id: String
) {
    constructor(memoryLineActivityResponse: MemoryLineActivityResponse): this(
        memoryLineActivityResponse.id
    )
}