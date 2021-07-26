package com.remember.moment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.remember.repository.AuthRepository
import com.remember.repository.MomentDetailRepository

class MomentDetailViewModel(
    private val momentDetailRepository: MomentDetailRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val idMoment: String
        get() = _idMoment

    private lateinit var _idMoment: String
    private val _isOwner: MutableLiveData<Boolean> = MutableLiveData()

    val isOwner: LiveData<Boolean>
        get() = _isOwner

    fun setIsOwner(ownerId: String) {
        _isOwner.value = authRepository.userId() == ownerId
    }

    fun setIsOwner(isOwner: Boolean) {
        _isOwner.value = isOwner
    }

    fun setIdMoment(id: String) {
        _idMoment = id
    }

    fun detail() = momentDetailRepository.momentDetail(idMoment)

    fun deleteMoment() = momentDetailRepository.deleteMoment(idMoment)
}
