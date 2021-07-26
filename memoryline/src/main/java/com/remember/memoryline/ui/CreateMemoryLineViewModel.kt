package com.remember.memoryline.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.remember.common.adapters.SpinnerItems
import com.remember.common.viewmodel.BaseViewModelPagination
import com.remember.repository.CreateMemoryLineRepository
import com.remember.repository.SearchRepository
import com.remember.repository.TypesRepository
import com.remember.repository.model.search.UserSearch

class CreateMemoryLineViewModel(
    private val typesRepository: TypesRepository,
    private val createMemoryLineRepository: CreateMemoryLineRepository,
    private val searchRepository: SearchRepository
) : BaseViewModelPagination() {

    private var _type: SpinnerItems? = null

    val type: SpinnerItems?
        get() = _type

    fun setType(spinnerItems: SpinnerItems) {
        _type = spinnerItems
    }

    fun types() = typesRepository.memoryLineTypes(page)

    fun createMemoryLine(name: String) = createMemoryLineRepository.createMemoryLine(
        name,
        type?.id ?: "",
        _participantsChosen.value?.map { it.id } ?: listOf()
    )

    private val _participantsSelected: MutableLiveData<List<UserSearch>> = MutableLiveData()

    val participantsSelected: LiveData<List<UserSearch>>
        get() = _participantsSelected

    private val _participantsChosen: MutableLiveData<List<UserSearch>> = MutableLiveData()

    val participantsChosen: LiveData<List<UserSearch>>
        get() = _participantsChosen

    var username: String = ""

    fun setupParticipantSelected() {
        _participantsSelected.value = _participantsChosen.value
    }

    fun setParticipantChosen() {
        _participantsChosen.value = _participantsSelected.value
        _participantsSelected.value = null
    }

    fun addParticipant(userSearch: UserSearch) {
        if(_participantsSelected.value?.find { it.id == userSearch.id } == null) {
            _participantsSelected.value = _participantsSelected.value?.toMutableList()?.apply { add(userSearch) }
                ?: listOf(userSearch)
        }
    }

    fun removeParticipant(userSearch: UserSearch) {
        _participantsSelected.value = _participantsSelected.value?.toMutableList()?.apply { remove(userSearch) }
    }

    fun search() = searchRepository.searchProfile(username)

}
