package com.remember.onboarding.ui

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class InputEmailFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun openFragment_shouldShowRightText() {
        inputEmailFragment {

        } launch {

        } check {
            showRightText()
        }
    }

    @Test
    fun inputInvalidEmail_andClickContinueButton_shouldShowErrorMessage() {
        inputEmailFragment {

        } launch {
            inputInvalidEmail()
            clickContinueButton()
        } check {
            errorMessage()
        }
    }

    @Test
    fun inputValidEmail_andClickContinueButton_butVerifyEmailCallFailed_shouldShowErrorMessage() {
        inputEmailFragment {
            verifyEmailFail()
        } launch {
            inputValidEmail()
            clickContinueButton()
        } check {
            errorMessage()
        }
    }

    @Test
    fun inputValidEmail_andClickContinueButton_butVerifyEmailCallSucceed_shouldGoToNextScreen() {
        inputEmailFragment {
            verifyEmailSucceed()
        } launch {
            inputValidEmail()
            clickContinueButton()
        } check {
            goToNextScreen()
        }
    }
}