package com.remember.onboarding.ui

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.DisableAnimationsRule
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class InputUsernameFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @get:Rule
    val disableAnimationsRule = DisableAnimationsRule()

    @Test
    fun openFragment_shouldShowRightText() {
        inputUsernameFragment {

        } launch {

        } check {
            showRightTexts()
        }
    }

    @Test
    fun clickSwitchUseNickname_andShouldShowNicknameAsUsername() {
        inputUsernameFragment {
            setupNickname()
        } launch {
            clickSwitchUseNickname()
        } check {
            showNicknameAsUsername()
        }
    }

    @Test
    fun notInputUsernameAndNotClickSwitch_andClickContinueButton_shouldShowErrorMessageInputValues() {
        inputUsernameFragment {
        } launch {
            clickContinueButton()
        } check {
            errorMessageInputValues()
        }
    }

    @Test
    fun inputUsernameWithVerifyUsernameFail_andClickContinueButton_shouldShowErrorMessageInvalidUsername() {
        inputUsernameFragment {
            verifyUsernameFail()
        } launch {
            inputUsername()
            clickContinueButton()
        } check {
            errorMessageInvalidUsername()
        }
    }

    @Test
    fun inputUsernameWithVerifyUsernameSucceedAndSignupFail_andClickContinueButton_shouldShowErrorMessageFailedToCreateAccount() {
        inputUsernameFragment {
            verifyUsernameSucceed()
            signupFail()
        } launch {
            inputUsername()
            clickContinueButton()
        } check {
            errorMessageFailedToCreateAccount()
        }
    }

    @Test
    fun inputUsernameWithVerifyUsernameSucceedAndSignupSucceed_andClickContinueButton_shouldGoToNextScreen() {
        inputUsernameFragment {
            verifyUsernameSucceed()
            signupSucceed()
        } launch {
            inputUsername()
            clickContinueButton()
        } check {
            goToNextScreen()
        }
    }
}