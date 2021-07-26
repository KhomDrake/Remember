package com.remember.memoryline

import com.remember.common.initializer.Modules
import com.remember.memoryline.ui.CreateMemoryLineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MemoryLineModule : Modules() {
    override fun loadModule() = listOf(module {
        viewModel { CreateMemoryLineViewModel(get(), get(), get()) }
    })
}