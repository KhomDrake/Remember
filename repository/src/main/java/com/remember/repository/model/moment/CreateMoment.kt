package com.remember.repository.model.moment

import com.remember.network.models.moment.CreateMomentResponse
import com.remember.repository.model.participant.AccountImage

class CreateMoment(
    val id: String,
    val file: String,
    val description: String,
    val owner: AccountImage,
    val idMemoryLine: String,
    val createdAt: String,
    val updatedAt: String
)

fun CreateMomentResponse.toCreateMoment() = CreateMoment(
    id,
    file,
    description,
    AccountImage(owner),
    idMemoryLine,
    createdAt,
    updatedAt
)