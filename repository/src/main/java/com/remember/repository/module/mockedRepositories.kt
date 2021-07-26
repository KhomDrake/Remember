package com.remember.repository.module

import androidx.annotation.VisibleForTesting
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
import io.mockk.mockk
import org.koin.dsl.module

@VisibleForTesting
val mockedRepositories = module {
    single { mockk<AuthRepository>(relaxed = true) }
    single { mockk<CreateMemoryLineRepository>(relaxed = true) }
    single { mockk<InviteRepository>(relaxed = true) }
    single { mockk<MemoryLineConfigurationRepository>(relaxed = true) }
    single { mockk<MemoryLineRepository>(relaxed = true) }
    single { mockk<MomentDetailRepository>(relaxed = true) }
    single { mockk<MomentCommentsRepository>(relaxed = true) }
    single { mockk<MomentsRepository>(relaxed = true) }
    single { mockk<ProfileRepository>(relaxed = true) }
    single { mockk<SearchRepository>(relaxed = true) }
    single { mockk<TypesRepository>(relaxed = true) }
    single { mockk<TermsRepository>(relaxed = true) }
    single { mockk<NotificationRepository>(relaxed = true) }
    single { mockk<CreateMomentRepository>(relaxed = true) }
    single { mockk<HistoryRepository>(relaxed = true) }
}