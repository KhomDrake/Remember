package com.remember.repository.model.invite

import com.remember.network.models.invite.InviteResponse
import com.remember.network.models.invite.InvitesPaginationResponse
import com.remember.network.models.invite.MemoryLineInviteResponse
import com.remember.repository.model.account.Account

class InvitesPagination(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<Invite>
)

fun InvitesPaginationResponse.toInvitesPagination() = InvitesPagination(
    next,
    previous,
    count,
    results.map { Invite(it) }
)

class Invite(
    val id: String,
    val answered: Boolean,
    val accepted: Boolean,
    val memoryLine: MemoryLineInvite,
    val owner: Account,
    val guest: String
) {
    constructor(invite: InviteResponse) : this(
        invite.id,
        invite.answered,
        invite.accepted,
        MemoryLineInvite(invite.memoryLine),
        Account(invite.owner),
        invite.guest
    )
}

class MemoryLineInvite(
    val id: String,
    val title: String,
    val description: String
) {
    constructor(memoryLineInviteResponse: MemoryLineInviteResponse): this(
        memoryLineInviteResponse.id,
        memoryLineInviteResponse.title,
        memoryLineInviteResponse.description
    )
}