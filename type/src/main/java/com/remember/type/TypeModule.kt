package com.remember.type

import com.remember.common.initializer.Modules
import com.remember.type.ui.MemoryLineTypeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class TypeModule: Modules() {
    override fun loadModule() = listOf(module {
        viewModel { MemoryLineTypeViewModel(get()) }
    })
}