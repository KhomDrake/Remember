package com.remember.repository.model.invite

import com.remember.network.models.invite.AnswerInviteResponse

class AnswerInvite(
    val accepted: Boolean
)

fun AnswerInviteResponse.toAnswerInvite() = AnswerInvite(accepted)