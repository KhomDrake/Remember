package com.remember.repository.model.moment

import com.remember.network.models.moment.MomentResponse
import com.remember.repository.model.pagination.DataPagination
import com.remember.repository.model.participant.AccountImage

class Moment(
    val idMoment: String,
    val owner: AccountImage,
    val description: String?,
    val idMemoryLine: String,
    val urlBucket: String,
    val commentsCount: Int,
    val creationDate: String,
    val updateAt: String
) : DataPagination(idMoment) {
    constructor(momentResponse: MomentResponse) : this(
        momentResponse.idMoment,
        AccountImage(momentResponse.owner),
        momentResponse.description,
        momentResponse.idMemoryLine,
        momentResponse.urlBucket,
        momentResponse.commentsCount,
        momentResponse.creationDate,
        momentResponse.updateAt
    )
}

fun MomentResponse.toMoment() = Moment(
    idMoment,
    AccountImage(owner),
    description,
    idMemoryLine,
    urlBucket,
    commentsCount,
    creationDate,
    updateAt
)
