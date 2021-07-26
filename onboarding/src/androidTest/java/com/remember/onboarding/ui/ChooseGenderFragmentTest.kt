package com.remember.onboarding.ui

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class ChooseGenderFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun openFragment_shouldShowRightTexts() {
        chooseGenderFragment {

        } launch {

        } check {
            rightTexts()
        }
    }

    @Test
    fun clickOption_optionShouldBeChecked() {
        chooseGenderFragment {

        } launch {
            chooseWomanOption()
        } check {
            womanOptionSelected()
        }
    }

    @Test
    fun clickOption_andClickContinueButton_shouldGoToNextScreen() {
        chooseGenderFragment {

        } launch {
            chooseWomanOption()
            clickContinueButton()
        } check {
            goToNextScreen()
        }
    }

}