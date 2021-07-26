package com.remember.remember.ui

import com.remember.common.initializer.Modules
import com.remember.common.ui.memory_line.MemoryLineViewModel
import com.remember.remember.ui.configuration.ConfigurationViewModel
import com.remember.remember.ui.history.HistoryViewModel
import com.remember.remember.ui.home.HomeViewModel
import com.remember.remember.ui.home.memory_line.MemoryLineDetailViewModel
import com.remember.remember.ui.home.memory_line.configuration.MemoryLineConfigurationViewModel
import com.remember.remember.ui.home.memory_line.participants.MemoryLineAddParticipantViewModel
import com.remember.remember.ui.home.memory_line.participants.MemoryLineParticipantsViewModel
import com.remember.remember.ui.notifications.NotificationsViewModel
import com.remember.remember.ui.terms.TermViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class RememberModule : Modules() {
    override fun loadModule() = listOf(
        module {
            viewModel { HomeViewModel(get(), get()) }
            viewModel { UserViewModel(get()) }
            viewModel { MemoryLineDetailViewModel(get(), get()) }
            viewModel { MemoryLineViewModel(get()) }
            viewModel {
                MemoryLineConfigurationViewModel(get(), get(), get())
            }
            viewModel {
                MemoryLineAddParticipantViewModel(
                    get(),
                    get()
                )
            }
            viewModel { TermViewModel(get()) }
            viewModel { HistoryViewModel(get()) }
            viewModel { ConfigurationViewModel(get()) }
            viewModel { MemoryLineParticipantsViewModel(get()) }
            viewModel { NotificationsViewModel(get(), get()) }
        }
    )
}