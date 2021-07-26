package com.remember.remember.ui.home.memory_line

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.DisableAnimationsRule
import com.remember.test.rule.IntentRule
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class MemoryLineFragmentTest {

    @get:Rule
    val koinRule = KoinRule(
        KoinModules.modules + mockedRepositories,
        start = false,
        configAndroidThreeTen = false
    )

    @get:Rule
    val intentRule = IntentRule()

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    @Test
    fun openWithData_shouldShowRightInformation() {
        memoryLineFragment {
            withData()
        } launch {

        } check {
            rightInformation()
        }
    }

    @Test
    fun openWithData_andClickCamera_shouldGoToCreateMomentWithIdMemoryLine() {
        memoryLineFragment {
            withData()
        } launch {
            clickCamera()
        } check {
            goToCameraWithIdMemoryLine()
        }
    }

    @Test
    fun openWithData_andClickAvatar_shouldGoToProfile() {
        memoryLineFragment {
            withData()
        } launch {
            clickAvatar()
        } check {
            goToProfile()
        }
    }

    @Test
    fun openWithData_andClickConfig_shouldGoToConfigurationMemoryLine() {
        memoryLineFragment {
            withData()
        } launch {
            clickConfig()
        } check {
            goToConfigurationMemoryLine()
        }
    }

    @Test
    fun openWithData_clickAddMoment_shouldShowTwoOptions() {
        memoryLineFragment {
            withData()
        } launch {
            clickAddMoment()
        } check {
            twoOptions()
        }
    }

    @Test
    fun whenOptionsIsDisplayed_andClickFirstOption_shouldGoToGallery() {
        memoryLineFragment {
            withData()
        } launch {
            clickAddMoment()
            clickFirstOption()
        } check {
            goToGallery()
        }
    }

    @Test
    fun whenOptionsIsDisplayed_andClickSecondOption_shouldGoToCamera() {
        memoryLineFragment {
            withData()
        } launch {
            clickAddMoment()
            clickSecondOption()
        } check {
            goToCamera()
        }
    }

    @Test
    fun openWithError_shouldShowErrorInformation() {
        memoryLineFragment {
            withError()
        } launch {

        } check {
            errorScreen()
        }
    }

    @Test
    fun openWithDataWithoutParticipants_shouldNotShowParticipantsIcon() {
        memoryLineFragment {
            withDataWithoutParticipants()
        } launch {

        } check {
            participantsIconNotDisplayed()
        }
    }

    @Test
    fun openWithDataWithParticipants_shouldShowParticipantsIcon() {
        memoryLineFragment {
            withData()
        } launch {

        } check {
            participantsIconDisplayed()
        }
    }

    @Test
    fun openWithDataWithParticipants_andClickParticipantsIcon_shouldGoToParticipants() {
        memoryLineFragment {
            withData()
        } launch {
            clickParticipantsIcon()
        } check {
            goToParticipants()
        }
    }

}