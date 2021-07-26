package com.remember.repository.model.moment

import com.remember.network.models.moment.MomentsPaginationResponse

class MomentsPagination(
    val next: String?,
    val previous: String?,
    val count: Int,
    val results: List<Moment>
)

fun MomentsPaginationResponse.toMomentsPagination() = MomentsPagination(
    next,
    previous,
    count,
    results.map { Moment(it) }
)