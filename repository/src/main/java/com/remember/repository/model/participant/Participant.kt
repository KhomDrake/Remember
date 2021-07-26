package com.remember.repository.model.participant

import com.remember.network.models.memory_line.ParticipantImageResponse
import com.remember.network.models.memory_line.ParticipantResponse
import com.remember.network.models.participant.ParticipantMemoryLineResponse
import com.remember.network.models.participant.ParticipantPaginationResponse
import com.remember.network.models.profile.AccountImageResponse
import com.remember.repository.model.account.Account
import com.remember.repository.model.memoryline.Owner

class ParticipantPagination(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ParticipantFull>
)

fun ParticipantPaginationResponse.toParticipantPagination(owner: Owner) = ParticipantPagination(
    count,
    next,
    previous,
    results.map { ParticipantFull(it) }.toMutableList().apply {
        add(0, ParticipantFull(owner))
    }
)

class ParticipantFull(
    val idParticipant: String,
    val participant: Account,
    val createdAt: String,
    val updatedAt: String,
    val isOwner: Boolean = false
) {
    constructor(participantResponse: ParticipantMemoryLineResponse) : this(
        participantResponse.idParticipant,
        Account(participantResponse.participant),
        participantResponse.createdAt,
        participantResponse.updatedAt
    )

    constructor(owner: Owner): this(
        owner.id,
        Account(owner),
        "",
        ""
    )
}

class Participant(
    val id: String,
    val participant: AccountImage,
    val createdAt: String,
    val updatedAt: String
) {
    constructor(participantResponse: ParticipantResponse) : this(
        participantResponse.id,
        AccountImage(participantResponse.participant),
        participantResponse.createdAt,
        participantResponse.updatedAt
    )
}

class ParticipantImage(
    val id: String,
    val updatedAt: String,
    val photo: String? = null
) {
    constructor(participantImageResponse: ParticipantImageResponse) : this(
        participantImageResponse.id,
        participantImageResponse.updatedAt,
        participantImageResponse.photo
    )
}

class AccountImage(
    val id: String,
    val photo: String? = null
) {
    constructor(accountImageResponse: AccountImageResponse) : this(
        accountImageResponse.id,
        accountImageResponse.photo
    )
}