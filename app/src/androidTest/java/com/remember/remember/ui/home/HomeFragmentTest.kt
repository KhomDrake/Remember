package com.remember.remember.ui.home

import com.remember.common.initializer.KoinModules
import com.remember.common.ui.memory_line.MemoryLineViewModel
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.DisableAnimationsRule
import com.remember.test.rule.IntentRule
import com.remember.test.rule.KoinRule
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class HomeFragmentTest {

    @get:Rule
    val koinRule = KoinRule(
        KoinModules.modules + mockedRepositories + listOf(module(override = true) {
            viewModel { MemoryLineViewModel(get()) }
        }),
        start = false,
        configAndroidThreeTen = false
    )

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    @get:Rule
    val intentsRule = IntentRule()

    @Test
    fun openFragment_shouldShowRightInformation() {
        homeFragment {
            withData()
        } launch {

        } check {
            rightInformation()
        }
    }

    @Test
    fun openFragmentTypesEmpty_shouldShowEmptyState() {
        homeFragment {
            withTypesEmpty()
        } launch {

        } check {
            emptyState()
        }
    }

    @Test
    fun whenClickCamera_shouldOpenCreateMoment() {
        homeFragment {
            withData()
        } launch {
            clickCamera()
        } check {
            openCreateMoment()
        }
    }

    @Test
    fun whenClickAvatar_shouldGoToAvatar() {
        homeFragment {
            withData()
        } launch {
            clickAvatar()
        } check {
            goToAvatar()
        }
    }

    @Test
    fun whenClickMore_shouldShowTwoOptions() {
        homeFragment {
            withData()
        } launch {
            clickMore()
        } check {
            twoOptions()
        }
    }

    @Test
    fun whenClickMoreAndTypesEmpty_shouldShowOneOption() {
        homeFragment {
            withTypesEmpty()
        } launch {
            clickMore()
        } check {
            oneOption()
        }
    }

    @Test
    fun whenClickFirstOption_shouldGoToCreateMemoryLine() {
        homeFragment {
            withData()
        } launch {
            clickMore()
            clickCreateMemoryLine()
        } check {
            goToCreateMemoryLine()
        }
    }

    @Test
    fun whenClickSecondOption_shouldGoToMemoryLineType() {
        homeFragment {
            withData()
        } launch {
            clickMore()
            clickTypes()
        } check {
            goToMemoryLineTypes()
        }
    }

    @Test
    fun whenClickOpenCameraInEmptyState_shouldShowErrorMessage() {
        homeFragment {
            withTypesEmpty()
        } launch {
            clickCamera()
        } check {
            errorMessage()
        }
    }

    @Test
    fun whenHasData_shouldShowRightInformation() {
        homeFragment {
            withDataMemoryLine()
            withData()
        } launch {

        } check {
            rightInformationMemoryLine()
        }
    }

    @Test
    fun whenMemoryLineHasOneOrMoreMembers_shouldShowMembersIcon() {
        homeFragment {
            withMemoryLineThreeMembers()
            withData()
        } launch {

        } check {
            membersIcon()
        }
    }

    @Test
    fun whenMemoryLineHasTwoOrMoreMembers_andClickMembersIcon_shouldShowParticipantsMemoryLine() {
        homeFragment {
            withMemoryLineThreeMembers()
            withData()
        } launch {
            clickMembersIcon()
        } check {
            goToParticipants()
        }
    }

    @Test
    fun whenClickMemoryLineDetail_shouldGoToMemoryLine() {
        homeFragment {
            withDataMemoryLine()
            withData()
        } launch {
            clickMemoryLineDetail()
        } check {
            goToMemoryLine()
        }
    }

    @Test
    @Ignore("Fix me later, please")
    fun whenMemoryLinesFailed_shouldErrorScreen() {
        homeFragment {
            withError()
            withData()
        } launch {

        } check {
            errorScreen()
        }
    }

    @Test
    @Ignore("Fix me later, please")
    fun whenClickOneOfTheMoments_shouldGoToMemoryLine() {
        homeFragment {
            withMemoryLineMoments()
            withData()
        } launch {
            clickMoments()
        } check {
            goToMemoryLine()
        }
    }
}
