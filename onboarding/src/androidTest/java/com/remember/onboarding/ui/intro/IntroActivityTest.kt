package com.remember.onboarding.ui.intro

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.IntentRule
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class IntroActivityTest {

    @get:Rule
    val intentRule = IntentRule()

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun openActivity_showRightValues() {
        introActivity {

        } launch {

        } check {
            rightValues()
        }
    }

    @Test
    fun clickCreateAccount_shouldGoToOnboarding() {
        introActivity {

        } launch {
            clickCreateAccount()
        } check {
            goToOnboarding()
        }
    }

    @Test
    fun clickAlreadyHaveAccount_shouldGoToLogin() {
        introActivity {

        } launch {
            clickAlreadyHaveAccount()
        } check {
            goToLogin()
        }
    }
}