package com.remember.repository.model.moment

import com.remember.network.models.moment.MomentBodyRequest

class MomentBody(
    val idMemoryLine: String,
    val moment: String,
    val description: String
)

fun MomentBody.toMomentBodyRequest() = MomentBodyRequest(
    moment,
    description
)