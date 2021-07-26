package com.remember.onboarding.ui

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class InputPasswordFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun openFragment_shouldShowRightTexts() {
        inputPasswordFragment {

        } launch {

        } check {
            showRightTexts()
        }
    }

    @Test
    fun inputOnlyPassword_andClickContinueButton_shouldShowErrorMessageInformValues() {
        inputPasswordFragment {

        } launch {
            inputPassword()
            clickContinueButton()
        } check {
            errorMessageInformValues()
        }
    }

    @Test
    fun inputOnlyConfirmPassword_andClickContinueButton_shouldShowErrorMessageInformValues() {
        inputPasswordFragment {

        } launch {
            inputConfirmPassword()
            clickContinueButton()
        } check {
            errorMessageInformValues()
        }
    }

    @Test
    fun inputPasswordAndConfirmPasswordNotEquals_andClickContinueButton_shouldShowErrorMessagePasswordsNotEqual() {
        inputPasswordFragment {

        } launch {
            inputPasswordAndConfirmPasswordNotEquals()
            clickContinueButton()
        } check {
            errorMessagePasswordsNotEquals()
        }
    }

    @Test
    fun inputPasswordAndConfirmPasswordEqualsButNotValid_andClickContinueButton_shouldShowErrorMessagePasswordsNotValid() {
        inputPasswordFragment {

        } launch {
            inputPasswordAndConfirmPasswordEqualsButNotValid()
            clickContinueButton()
        } check {
            errorMessagePasswordsInvalid()
        }
    }

    @Test
    fun inputPasswordAndConfirmPasswordEqualsButValid_andClickContinueButton_shouldGoToNextScreen() {
        inputPasswordFragment {

        } launch {
            inputPasswordAndConfirmPasswordEqualsAndValid()
            clickContinueButton()
        } check {
            goToNextScreen()
        }
    }

}