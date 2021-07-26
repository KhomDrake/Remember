package com.remember.common.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModelPagination : ViewModel() {
    protected var page = 1
    private var _hasNextPage = false

    val hasNextPage: Boolean
        get() = _hasNextPage

    open fun reset() {
        page = 1
        _hasNextPage = false
    }

    fun resetPage() {
        reset()
    }

    fun setNextPage(next: String?) {
        _hasNextPage = !next.isNullOrEmpty()

        if(_hasNextPage) addPage()
    }

    private fun addPage() = ++page
}
