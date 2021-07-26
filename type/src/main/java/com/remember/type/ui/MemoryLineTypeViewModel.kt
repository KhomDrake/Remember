package com.remember.type.ui

import com.remember.common.viewmodel.BaseViewModelPagination
import com.remember.repository.TypesRepository
import com.remember.repository.model.MemoryLineType

class MemoryLineTypeViewModel(private val typesRepository: TypesRepository) : BaseViewModelPagination() {

    private val _types: MutableList<MemoryLineType> = mutableListOf()

    val types: List<MemoryLineType>
        get() = _types

    fun loadTypes() = typesRepository.memoryLineTypes(page).map(async = true) {
        _types.addAll(it.results)
        it
    }

    override fun reset() {
        super.reset()
        _types.removeAll(_types)
    }

    fun createType(name: String, priority: Int) = typesRepository.createMemoryLineType(name, priority)

    fun updateTypes(types: List<MemoryLineType>) = typesRepository.updateMemoryLineTypes(
        types.mapIndexed { index, memoryLineType ->
            memoryLineType.priority = index
            memoryLineType
        }
    )
}
