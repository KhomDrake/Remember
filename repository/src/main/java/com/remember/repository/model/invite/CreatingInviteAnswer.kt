package com.remember.repository.model.invite

import com.remember.network.models.invite.CreateInviteAnswerResponse

class CreateInviteAnswer(
    val guest: String,
    val memoryLine: String
)

fun CreateInviteAnswerResponse.toCreateInviteAnswer() = CreateInviteAnswer(
    guest, memoryLine
)