package com.remember.onboarding.ui

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.IntentRule
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class AccountCreatedFragmentTest {

    @get:Rule
    val intentRule = IntentRule()

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun whenOpenFragment_shouldShowRightTexts() {
        accountCreatedFragment {

        } launch {

        } check {
            rightTexts()
        }
    }

    @Test
    fun whenClickRememberHistory_shouldGoToRememberHistory() {
        accountCreatedFragment {
            mockGoToRememberHistory()
        } launch {
            clickRememberHistory()
        } check {
            goToRememberHistory()
        }
    }

    @Test
    fun whenClickFirstAccess_shouldGoToLogin() {
        accountCreatedFragment {
            mockGoToFirstAccess()
        } launch {
            clickFirstAccess()
        } check {
            goToFirstAccess()
        }
    }
}