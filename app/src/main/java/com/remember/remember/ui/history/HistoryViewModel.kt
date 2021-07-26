package com.remember.remember.ui.history

import androidx.lifecycle.ViewModel
import com.remember.repository.HistoryRepository

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {

    fun memoryLineHistory() = historyRepository.momentsHistory()

    fun participantsHistory() = historyRepository.participantsHistory()

}
