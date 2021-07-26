package com.remember.remember.ui.notifications

import androidx.lifecycle.ViewModel
import com.remember.repository.InviteRepository
import com.remember.repository.NotificationRepository

class NotificationsViewModel(
    private val notificationRepository: NotificationRepository,
    private val inviteRepository: InviteRepository
) : ViewModel() {
    fun answerInvite(idInvite: String, answer: Boolean) = inviteRepository.answerInvite(idInvite, answer)

    fun notification() = notificationRepository.notification()
}
