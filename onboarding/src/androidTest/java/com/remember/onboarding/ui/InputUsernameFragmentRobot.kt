package com.remember.onboarding.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.remember.common.extension.showError
import com.remember.onboarding.R
import com.remember.onboarding.ui.register.InputUsernameFragment
import com.remember.onboarding.ui.register.InputUsernameFragmentArgs
import com.remember.onboarding.ui.register.InputUsernameFragmentDirections
import com.remember.repository.AuthRepository
import com.remember.repository.InvalidUsername
import com.remember.test.extensions.*
import com.remember.test.utils.mockResponseRepository
import com.remember.test.utils.mockToast
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.inject

fun InputUsernameFragmentTest.inputUsernameFragment(func: InputUsernameFragmentRobot.() -> Unit) =
    InputUsernameFragmentRobot(mockk(relaxed = true)).apply(func)

class InputUsernameFragmentRobot(private val navController: NavController) : KoinComponent {

    private val authRepository: AuthRepository by inject()
    private var nickname = "wit"

    fun verifyUsernameFail() {
        mockResponseRepository(authRepository::createAccount).postError(InvalidUsername())
    }

    fun verifyUsernameSucceed() {
        mockResponseRepository(authRepository::createAccount).postData(Unit)
    }

    fun signupFail() {
        mockResponseRepository(authRepository::createAccount).postError(Throwable())
    }

    fun signupSucceed() {
        mockResponseRepository(authRepository::createAccount).postData(Unit)
    }

    fun setupNickname() {
        nickname = "Blackthorn"
    }

    infix fun launch(func: InputUsernameFragmentRobot.() -> Unit): InputUsernameFragmentRobot {
        mockToast()

        every {
            navController.navigate(InputUsernameFragmentDirections.actionInputUsernameFragmentToAccountCreatedFragment())
        } returns Unit

        launchFragmentInContainer<InputUsernameFragment>(
            fragmentArgs = InputUsernameFragmentArgs(
                "dsadasddsa",
                nickname,
                "05/09/1997",
                "dsadasddsa",
                "dsadasddsa",
                "dsadasddsa",
                "dsadasddsa"
            ).toBundle(),
            themeResId = R.style.RememberAppTheme_Primary
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }

        return apply(func)
    }

    fun clickSwitchUseNickname() {
        R.id.switch_username.click()
    }

    fun clickContinueButton() {
        R.id.input_username_continue.click(true)
    }

    fun inputUsername() {
        R.id.input_username.typeText("Wit")
    }

    infix fun check(func: InputUsernameFragmentResult.() -> Unit) =
        InputUsernameFragmentResult(navController).apply(func)
}

class InputUsernameFragmentResult(private val navController: NavController) {
    fun showRightTexts() {
        R.string.onboarding_input_username_title_text.isTextDisplayed()
        R.string.onboarding_input_username_switch_text.isTextDisplayed()
        R.string.onboarding_input_username_hintview_text.isTextDisplayed()
        R.string.input_username_continue_text.isTextDisplayed()
    }

    fun showNicknameAsUsername() {
        R.id.input_username.hasText("Blackthorn")
    }

    fun errorMessageInputValues() {
        verify { any<Fragment>().showError(
            R.string.onboarding_input_value, any()
        ) }
    }

    fun errorMessageInvalidUsername() {
        verify { any<Fragment>().showError(
            R.string.onboarding_username_invalid, any()
        ) }
    }

    fun errorMessageFailedToCreateAccount() {
        verify { any<Fragment>().showError(
            R.string.onboarding_account_could_not_be_created, any()
        ) }
    }

    fun goToNextScreen() {
        verify {
            navController.navigate(InputUsernameFragmentDirections.actionInputUsernameFragmentToAccountCreatedFragment())
        }
    }

}