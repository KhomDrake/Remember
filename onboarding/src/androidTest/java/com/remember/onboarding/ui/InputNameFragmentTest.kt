package com.remember.onboarding.ui

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class InputNameFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun openFragment_shouldShowRightTexts() {
        inputNameFragment {

        } launch {

        } check {
            showRightTexts()
        }
    }

    @Test
    fun onlyInputName_andClickContinueButton_shouldShowErrorMessage() {
        inputNameFragment {

        } launch {
            inputName()
            clickContinueButton()
        } check {
            errorMessage()
        }
    }

    @Test
    fun onlyInputNickname_andClickContinueButton_shouldShowErrorMessage() {
        inputNameFragment {

        } launch {
            inputNickname()
            clickContinueButton()
        } check {
            errorMessage()
        }
    }

    @Test
    fun inputNameAndNickname_andClickContinueButton_shouldGoToNextScreen() {
        inputNameFragment {

        } launch {
            inputName()
            inputNickname()
            clickContinueButton()
        } check {
            goToNextScreen()
        }
    }

}