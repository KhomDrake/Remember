package com.remember.repository.model.participant

import com.remember.network.models.participant.ParticipantSearchResponse
import com.remember.repository.model.search.UserSearch

class ParticipantSearch(
    val id: String,
    val nickname: String,
    val username: String,
    val photo: String?,
    val isOwner: Boolean = false
) {
    constructor(participantSearchResponse: ParticipantSearchResponse) : this(
        participantSearchResponse.id,
        participantSearchResponse.nickname,
        participantSearchResponse.username,
        participantSearchResponse.photo
    )

    constructor(participantFull: ParticipantFull) : this(
        participantFull.idParticipant,
        participantFull.participant.nickname,
        participantFull.participant.username,
        participantFull.participant.photo,
        participantFull.isOwner
    )

    constructor(userSearch: UserSearch) : this(
        userSearch.id,
        userSearch.nickname,
        userSearch.username,
        userSearch.picture
    )
}

fun ParticipantSearch.toUserSearch() = UserSearch(
    id,
    username,
    nickname,
    photo
)