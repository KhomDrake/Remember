package com.remember.remember.ui.configuration

import androidx.fragment.app.testing.launchFragmentInContainer
import com.remember.remember.R
import com.remember.repository.AuthRepository
import com.remember.test.extensions.*
import com.remember.test.utils.checkIntent
import com.remember.test.utils.mockIntent
import com.remember.test.utils.mockResponseRepository
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.inject

fun ConfigurationFragmentTest.configurationFragment(func: ConfigurationFragmentRobot.() -> Unit) =
    ConfigurationFragmentRobot().apply(func)

class ConfigurationFragmentRobot : KoinComponent {

    private val authRepository: AuthRepository by inject()

    infix fun launch(func: ConfigurationFragmentRobot.() -> Unit) : ConfigurationFragmentRobot {
        mockIntent("LOGO", targetContext = true)
        mockResponseRepository(authRepository::logout).postData(true)
        launchFragmentInContainer<ConfigurationFragment>(
            themeResId = R.style.RememberAppTheme_Primary
        )
        return apply(func)
    }

    infix fun check(func: ConfigurationFragmentCheck.() -> Unit) =
        ConfigurationFragmentCheck().apply(func)

    fun clickLeaveAccount() {
        R.id.leave_account_button.click()
    }
}

class ConfigurationFragmentCheck : KoinComponent {

    private val authRepository: AuthRepository by inject()

    fun rightInformation() {
        R.string.app_configuration_title.isTextDisplayed(true)
        R.string.app_config_notification_text.isTextDisplayed(true)
        R.string.app_config_memories_text.isTextDisplayed(true)
        R.string.app_config_invites_text.isTextDisplayed(true)
        R.string.app_config_notification_out_of_app_text.isTextDisplayed(true)
        R.string.app_button_leave_text.isTextDisplayed(true)
        R.string.app_button_delete_account_text.isTextDisplayed(true)
        R.id.delete_account_button.isDisable()
        R.id.leave_account_button.isEnable()
        R.id.version.isDisplayed()
    }

    fun shouldLeaveAccount() {
        verify {
            authRepository.logout()
        }
        checkIntent("LOGO", targetContext = true)
    }
}
