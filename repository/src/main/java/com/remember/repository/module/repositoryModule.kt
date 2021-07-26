package com.remember.repository.module

import com.remember.repository.AuthRepository
import com.remember.repository.CreateMemoryLineRepository
import com.remember.repository.CreateMomentRepository
import com.remember.repository.HistoryRepository
import com.remember.repository.InviteRepository
import com.remember.repository.MemoryLineConfigurationRepository
import com.remember.repository.MemoryLineRepository
import com.remember.repository.MomentCommentsRepository
import com.remember.repository.MomentDetailRepository
import com.remember.repository.MomentsRepository
import com.remember.repository.NotificationRepository
import com.remember.repository.ProfileRepository
import com.remember.repository.SearchRepository
import com.remember.repository.TermsRepository
import com.remember.repository.TypesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ProfileRepository(get()) }
    single { AuthRepository(get()) }
    single { MemoryLineRepository(get()) }
    single { MomentsRepository(get()) }
    single { MemoryLineConfigurationRepository(get()) }
    single { InviteRepository(get()) }
    single { CreateMemoryLineRepository(get()) }
    single { SearchRepository(get()) }
    single { MomentDetailRepository(get()) }
    single { MomentCommentsRepository(get()) }
    single { TypesRepository(get()) }
    single { TermsRepository(get()) }
    single { NotificationRepository(get()) }
    single { CreateMomentRepository(get()) }
    single { HistoryRepository(get()) }
}
