package com.remember.onboarding.ui

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class InputCellphoneFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun openFragment_shouldShowRightText() {
        inputCellphoneFragment {

        } launch {

        } check {
            rightTexts()
        }
    }

    @Test
    fun inputRightDDDAndNumber_shouldShowDDDAndNumber() {
        inputCellphoneFragment {

        } launch {
            inputDDD()
            inputNumber()
        } check {
            dddAndNumber()
        }
    }

    @Test
    fun inputRightDDDAndNumber_andClickContinueButton_shouldGoToNextScreen() {
        inputCellphoneFragment {

        } launch {
            inputDDD()
            inputNumber()
            clickContinueButton()
        } check {
            goToNextScreen()
        }
    }

    @Test
    fun inputWrongNumber_andClickContinueButton_shouldShowRightErrorMessage() {
        inputCellphoneFragment {

        } launch {
            inputDDD()
            inputWrongNumber()
            clickContinueButton()
        } check {
            errorMessageNumber()
        }
    }

    @Test
    fun inputWrongDDD_andClickContinueButton_shouldShowRightErrorMessage() {
        inputCellphoneFragment {

        } launch {
            inputWrongDDD()
            inputNumber()
            clickContinueButton()
        } check {
            errorMessageDDD()
        }
    }

}