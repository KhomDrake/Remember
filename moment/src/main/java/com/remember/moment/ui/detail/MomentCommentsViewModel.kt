package com.remember.moment.ui.detail

import com.remember.common.viewmodel.BaseViewModelPagination
import com.remember.repository.MomentCommentsRepository

class MomentCommentsViewModel(
    private val momentCommentsRepository: MomentCommentsRepository
) : BaseViewModelPagination() {

    private var idMoment: String = ""

    fun setIdMoment(id: String) {
        idMoment = id
    }

    fun comments() = momentCommentsRepository.commentsMoment(idMoment, page)

    fun insertComment(comment: String) = momentCommentsRepository.insertComment(idMoment, comment)

    fun notValidComment(comment: String) = comment.isEmpty()

}
