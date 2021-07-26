package com.remember.repository.model.memoryline

import com.remember.network.models.memory_line.MemoryLineResponse
import com.remember.network.models.memory_line.MomentMemoryLineResponse
import com.remember.repository.model.pagination.DataPagination
import com.remember.repository.model.participant.Participant

class MemoryLine(
    val title: String,
    val id: String,
    val description: String,
    val owner: Owner,
    val type: String,
    val moments: List<MomentMemoryLine>,
    val participants: List<Participant>,
    val creationDate: String,
    val updatedDate: String
) : DataPagination(id){
    constructor(memoryLineResponse: MemoryLineResponse) : this(
        memoryLineResponse.title,
        memoryLineResponse.id,
        memoryLineResponse.description,
        Owner(memoryLineResponse.owner),
        memoryLineResponse.type,
        memoryLineResponse.moments.map { MomentMemoryLine(it) },
        memoryLineResponse.participants.map { Participant(it) },
        memoryLineResponse.creationDate,
        memoryLineResponse.updatedDate
    )
}

fun MemoryLineResponse.toMemoryLine() = MemoryLine(
    title,
    id,
    description,
    Owner(owner),
    type,
    moments.map { MomentMemoryLine(it) },
    participants.map { Participant(it) },
    creationDate,
    updatedDate
)

class MomentMemoryLine(
    val id: String,
    val file: String
) {
    constructor(momentMemoryLineResponse: MomentMemoryLineResponse) : this(
        momentMemoryLineResponse.id,
        momentMemoryLineResponse.file
    )
}