package com.remember.onboarding.ui

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class BirthDateFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun openFragment_shouldShowRightTexts() {
        birthDateFragment {

        } launch {

        } check {
            rightText()
        }
    }

    @Test
    fun inputValidBirthDate_andClickContinueButton_shouldGoToNextScreen() {
        birthDateFragment {

        } launch {
            inputValidBirthDate()
            clickContinueButton()
        } check {
            goToNextScreen()
        }
    }



}