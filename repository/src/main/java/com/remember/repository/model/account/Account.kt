package com.remember.repository.model.account

import com.remember.network.models.profile.AccountResponse
import com.remember.repository.model.memoryline.Owner

class Account(
    val id: String,
    val photo: String? = null,
    val name: String,
    val nickname: String,
    val username: String
) {
    constructor(accountResponse: AccountResponse): this(
        accountResponse.id,
        accountResponse.photo,
        accountResponse.name,
        accountResponse.nickname,
        accountResponse.username
    )

    constructor(owner: Owner): this(
        owner.id,
        owner.photo,
        owner.name,
        owner.nickname,
        owner.username
    )
}
