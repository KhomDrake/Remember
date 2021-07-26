package com.remember.repository.model

import com.remember.network.models.type.MemoryLineTypePaginationResponse
import com.remember.network.models.type.MemoryLineTypeResponse
import com.remember.repository.model.pagination.DataPagination

class MemoryLineType(
    val idTypeUser: String,
    val idType: String,
    val name: String,
    var priority: Int
) : DataPagination(idType) {
    constructor(memoryLineTypeResponse: MemoryLineTypeResponse): this(
        memoryLineTypeResponse.idTypeOwner,
        memoryLineTypeResponse.type.id,
        memoryLineTypeResponse.type.name,
        memoryLineTypeResponse.priority
    )
}

class MemoryLineTypePagination(
    val next: String?,
    val results: List<MemoryLineType>
) {
    constructor(memoryLineTypePaginationResponse: MemoryLineTypePaginationResponse): this(
        memoryLineTypePaginationResponse.next, memoryLineTypePaginationResponse.results.map { MemoryLineType(it) }
    )
}