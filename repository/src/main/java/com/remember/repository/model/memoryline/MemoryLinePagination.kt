package com.remember.repository.model.memoryline

import com.remember.network.models.memory_line.MemoryLinePaginationResponse

class MemoryLinePagination(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<MemoryLine>
)

fun MemoryLinePaginationResponse.toMemoryLinePagination() = MemoryLinePagination(
    next,
    previous,
    count,
    results.map { MemoryLine(it) }
)