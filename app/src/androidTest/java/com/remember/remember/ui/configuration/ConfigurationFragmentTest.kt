package com.remember.remember.ui.configuration

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.IntentRule
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class ConfigurationFragmentTest {

    @get:Rule
    val koinRule = KoinRule(
        KoinModules.modules + mockedRepositories,
        start = false,
        configAndroidThreeTen = false
    )

    @get:Rule
    val intentsRule = IntentRule()

    @Test
    fun whenOpenConfigurationFragment_shouldRightInformation() {
        configurationFragment {

        } launch {

        } check {
            rightInformation()
        }
    }

    @Test
    fun whenClickLeaveAccount_shouldLeaveAccount() {
        configurationFragment {

        } launch {
            clickLeaveAccount()
        } check {
            shouldLeaveAccount()
        }
    }

}