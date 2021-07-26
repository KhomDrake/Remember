package com.remember.moment

import com.remember.common.initializer.Modules
import com.remember.moment.ui.create.MomentViewModel
import com.remember.moment.ui.detail.MomentCommentsViewModel
import com.remember.moment.ui.detail.MomentDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MomentModule : Modules() {
    override fun loadModule() = listOf(module {
        viewModel { MomentViewModel(get(), get(), get()) }
        viewModel { MomentDetailViewModel(get(), get()) }
        viewModel { MomentCommentsViewModel(get()) }
    })
}
