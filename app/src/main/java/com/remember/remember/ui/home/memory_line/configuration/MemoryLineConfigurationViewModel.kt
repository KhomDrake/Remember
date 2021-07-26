package com.remember.remember.ui.home.memory_line.configuration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.remember.common.model.MemoryLineBaseInformation
import com.remember.repository.AuthRepository
import com.remember.repository.MemoryLineConfigurationRepository
import com.remember.repository.ProfileRepository

class MemoryLineConfigurationViewModel(
    private val repository: MemoryLineConfigurationRepository,
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _memoryLineBaseInformation: MutableLiveData<MemoryLineBaseInformation> = MutableLiveData()

    val memoryLineBaseInformation: LiveData<MemoryLineBaseInformation>
        get() = _memoryLineBaseInformation

    val idMemoryLine: String
        get() = _memoryLineBaseInformation.value?.idMemoryLine ?: ""

    val nameMemoryLine: String
        get() = _memoryLineBaseInformation.value?.name ?: ""

    fun setNameMemoryLine(newName: String) {
        _memoryLineBaseInformation.value?.let {
            _memoryLineBaseInformation.postValue(it.apply {
                name = newName
            })
        }
    }

    fun userId() = authRepository.userId()

    fun profile() = profileRepository.informationProfile()

    fun setMemoryLineInformation(memoryLineBaseInformation: MemoryLineBaseInformation) {
        _memoryLineBaseInformation.value = memoryLineBaseInformation
    }

    fun participants() = repository.memoryLineParticipants(idMemoryLine)

    fun updateMemoryLineName(newName: String) = repository.updateMemoryLine(
        idMemoryLine, newName
    )

    fun deleteMemoryLine() = repository.deleteMemoryLine(idMemoryLine)
}