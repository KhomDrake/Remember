package com.remember.remember.ui.home.memory_line

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.remember.common.viewmodel.BaseViewModelPagination
import com.remember.common.model.MemoryLineBaseInformation
import com.remember.repository.MomentsRepository
import com.remember.repository.ProfileRepository
import com.remember.repository.cache.CacheBox

class MemoryLineDetailViewModel(
    private val repository: MomentsRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModelPagination() {

    private val _memoryLineBaseInformation: MutableLiveData<MemoryLineBaseInformation> = MutableLiveData()

    val baseInformationMemoryLine: LiveData<MemoryLineBaseInformation>
        get() = _memoryLineBaseInformation

    fun setBaseInformationMemoryLine(memoryLineBaseInformation: MemoryLineBaseInformation) {
        _memoryLineBaseInformation.value = memoryLineBaseInformation
    }

    override fun reset() {
        super.reset()
        CacheBox.momentsHashMap.clear()
    }

    fun profile() = profileRepository.informationProfile()

    fun setNameMemoryLine(newName: String) {
        _memoryLineBaseInformation.value?.name = newName
    }

    fun momentsPagination() = repository.momentsPagination(
        _memoryLineBaseInformation.value?.idMemoryLine ?: "",
        page
    )

    fun memoryLineDetail() = repository.detailMemoryLine(_memoryLineBaseInformation.value?.idMemoryLine ?: "")
}
