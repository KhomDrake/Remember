package com.remember.remember.ui.home.memory_line.participants

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class MemoryLineAddParticipantFragmentTest {

    @get:Rule
    val koinRule = KoinRule(
        KoinModules.modules + mockedRepositories,
        start = false,
        configAndroidThreeTen = false
    )

    @Test
    fun clickAddParticipantsWithoutParticipantsSelected_shouldShowErrorMessage() {
        memoryLineAddParticipants {

        } launch {
            clickAddParticipantsButton()
        } check {
            errorMessageNoParticipantsSelected()
        }
    }

    @Test
    fun inputNewUserInvalid_shouldNotShowSearchIcon() {
        memoryLineAddParticipants {

        } launch {
            inputInvalidUser()
        } check {
            searchIconNotDisplayed()
        }
    }

    @Test
    fun inputNewUserValid_shouldShowSearchIcon() {
        memoryLineAddParticipants {

        } launch {
            inputValidUser()
        } check {
            searchIconDisplayed()
        }
    }

    @Test
    fun clickSearchIcon_shouldShowLoadUsers() {
        memoryLineAddParticipants {
            withSearchUsers()
        } launch {
            inputValidUser()
            clickSearchIcon()
        } check {
            loadUsersCalled()
            usersLoaded()
        }
    }

    @Test
    fun loadUsers_andClickFirst_shouldHaveOneUserSelected() {
        memoryLineAddParticipants {
            withSearchUsers()
        } launch {
            inputValidUser()
            clickSearchIcon()
            clickFirstSearchedUsers()
        } check {
            oneUserSelected()
        }
    }

    @Test
    fun loadUsers_andClickFirst_andAfterClickRemove_shouldNotHaveUsersSelected() {
        memoryLineAddParticipants {
            withSearchUsers()
        } launch {
            inputValidUser()
            clickSearchIcon()
            clickFirstSearchedUsers()
            clickToRemoveFirstSearchedUsers()
        } check {
            hasNoUserSelected()
        }
    }

    @Test
    fun callAddParticipantsWithError_shouldShowErrorMessages() {
        memoryLineAddParticipants {
            withSearchUsers()
            withAddParticipantsError()
        } launch {
            inputValidUser()
            clickSearchIcon()
            clickFirstSearchedUsers()
            clickAddParticipantsButton()
        } check {
            errorMessageAddParticipants()
        }
    }

    @Test
    fun callAddParticipantsWithSuccess_shouldShowSuccessMessages() {
        memoryLineAddParticipants {
            withAddParticipantsWithSuccess()
            withSearchUsers()
        } launch {
            inputValidUser()
            clickSearchIcon()
            clickFirstSearchedUsers()
            clickAddParticipantsButton()
        } check {
            successMessageAddParticipants()
        }
    }
}