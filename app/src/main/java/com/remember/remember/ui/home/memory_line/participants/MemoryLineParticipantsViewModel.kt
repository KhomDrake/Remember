package com.remember.remember.ui.home.memory_line.participants

import androidx.lifecycle.ViewModel
import com.remember.repository.MemoryLineRepository

class MemoryLineParticipantsViewModel(
    private val memoryLineRepository: MemoryLineRepository
) : ViewModel() {

    private var _idMemoryLine: String = ""

    fun setIdMemoryLine(id: String) {
        _idMemoryLine = id
    }

    fun participants() = memoryLineRepository.memoryLineParticipants(_idMemoryLine)
}
