package com.remember.profile.ui

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class ProfileFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun whenInformationProfileSuccess_shouldShowRightValues() {
        profileFragment {
            profileSuccess()
        } launch {

        } check {
            successValues()
        }
    }

    @Test
    fun whenInformationProfileError_shouldShowErrorScreen() {
        profileFragment {
            profileError()
        } launch {

        } check {
            errorScreen()
        }
    }

    @Test
    fun whenShowErrorScreen_andClickTryAgain_shouldRequestProfile() {
        profileFragment {
            profileError()
        } launch {
            clickTryAgain()
        } check {
            requestedProfileAgain()
        }
    }

    @Test
    fun whenProfileSuccess_andClickUpdateProfilePicture_shouldShowBottomSheet() {
        profileFragment {
            profileSuccess()
        } launch {
            clickUpdateProfilePicture()
        } check {
            bottomSheetVisible()
        }
    }

    @Test
    fun whenBottomSheetOpen_andClickRemove_shouldCallRemoveProfilePicture() {
        profileFragment {
            profileSuccess()
            removeProfileSuccess()
        } launch {
            clickUpdateProfilePicture()
            clickRemove()
        } check {
            removeProfileCalled()
        }
    }

    @Test
    fun whenBottomSheetOpenAndRemoveFailure_andClickRemove_shouldShowFailureMessage() {
        profileFragment {
            profileSuccess()
            removeProfileFailure()
        } launch {
            clickUpdateProfilePicture()
            clickRemove()
        } check {
            failureMessage()
        }
    }

    @Test
    fun whenBottomSheetOpenAndRemoveSuccess_andClickRemove_shouldShowSuccessMessage() {
        profileFragment {
            profileSuccess()
            removeProfileSuccess()
        } launch {
            clickUpdateProfilePicture()
            clickRemove()
        } check {
            successMessage()
        }
    }
}