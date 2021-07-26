package com.remember.common.ui.memory_line

import com.remember.common.viewmodel.BaseViewModelPagination
import com.remember.repository.MemoryLineRepository
import com.remember.repository.cache.CacheBox

class MemoryLineViewModel(private val memoryLineRepository: MemoryLineRepository) : BaseViewModelPagination() {

    override fun reset() {
        super.reset()
        CacheBox.memoryLineHashMap.clear()
    }

    fun memoryLineByType(typeMemoryLine: String) = memoryLineRepository.memoryLine(typeMemoryLine, page)
}
