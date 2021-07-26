package com.remember.remember.ui.home.memory_line.participants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.remember.repository.InviteRepository
import com.remember.repository.SearchRepository
import com.remember.repository.model.search.UserSearch

class MemoryLineAddParticipantViewModel(
    private val inviteRepository: InviteRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _participantsSelected: MutableLiveData<List<UserSearch>> = MutableLiveData()

    val participantsSelected: LiveData<List<UserSearch>>
        get() = _participantsSelected

    var username: String = ""

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

    private lateinit var _idMemoryLine: String

    fun setIdMemoryLine(id: String) {
        _idMemoryLine = id
    }

    fun createInvites(participants: List<UserSearch>) = inviteRepository.createInvites(_idMemoryLine, participants.map { it.id })
}
