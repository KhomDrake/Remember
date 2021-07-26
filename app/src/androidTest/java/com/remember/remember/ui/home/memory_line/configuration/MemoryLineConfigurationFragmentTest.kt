package com.remember.remember.ui.home.memory_line.configuration

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.DisableAnimationsRule
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class MemoryLineConfigurationFragmentTest {

    @get:Rule
    val koinRule = KoinRule(
        KoinModules.modules + mockedRepositories,
        start = false,
        configAndroidThreeTen = false
    )

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    @Test
    fun openWithData_shouldShowRightInformation() {
        memoryLineConfiguration {
        } launch {

        } check {
            rightInformation()
        }
    }

    @Test
    fun openWithData_andIsOwner_shouldShowDeleteMemoryLine() {
        memoryLineConfiguration {
            isOwner()
        } launch {

        } check {
            deleteMemoryLineEnable()
        }
    }

    @Test
    fun openWithData_andIsNotOwner_shouldShowDeleteMemoryLineDisable() {
        memoryLineConfiguration {
            isNotOwner()
        } launch {

        } check {
            deleteMemoryLineDisable()
        }
    }

    @Test
    fun openWithData_andIsOwnerAndClickDeleteMemoryLine_shouldShowDeleteMemoryLineBottomSheet() {
        memoryLineConfiguration {
            isOwner()
        } launch {
            clickDeleteMemoryLine()
        } check {
            bottomSheetDeleteMemoryLine()
        }
    }

    @Test
    fun whenDeleteMemoryLineBottomSheetOpen_andClickCancel_shouldNotCallDeleteMemoryLine() {
        memoryLineConfiguration {
            isOwner()
        } launch {
            clickDeleteMemoryLine()
            clickCancel()
        } check {
            notCalledDeleteMemoryLine()
        }
    }

    @Test
    fun whenDeleteMemoryLineBottomSheetOpen_andClickConfirm_shouldCallDeleteMemoryLine() {
        memoryLineConfiguration {
            isOwner()
        } launch {
            clickDeleteMemoryLine()
            clickConfirm()
        } check {
            calledDeleteMemoryLine()
        }
    }

    @Test
    fun openWithData_andClickAddNewParticipants_shouldGoToAddParticipants() {
        memoryLineConfiguration {
        } launch {
            clickAddNewParticipants()
        } check {
            goToAddParticipants()
        }
    }

    @Test
    fun openWithData_andClickParticipants_shouldGoToParticipants() {
        memoryLineConfiguration {
        } launch {
            clickParticipants()
        } check {
            goToParticipants()
        }
    }

    @Test
    fun openWithData_clickEditName_shouldShowBottomSheet() {
        memoryLineConfiguration {
            isOwner()
        } launch {
            clickEditName()
        } check {
            bottomSheetEditName()
        }
    }

    @Test
    fun openWithData_clickEditName_InputNewNameAndClickCancel_shouldNotCallUpdateName() {
        memoryLineConfiguration {
            isOwner()
        } launch {
            clickEditName()
            clickCancel()
        } check {
            notCalledUpdateName()
        }
    }
}