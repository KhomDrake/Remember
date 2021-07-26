package com.remember.onboarding.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.remember.common.extension.showError
import com.remember.onboarding.R
import com.remember.onboarding.ui.register.InputPasswordFragment
import com.remember.onboarding.ui.register.InputPasswordFragmentArgs
import com.remember.onboarding.ui.register.InputPasswordFragmentDirections
import com.remember.test.extensions.click
import com.remember.test.extensions.isTextDisplayed
import com.remember.test.utils.mockToast
import com.remember.test.extensions.typeText
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

fun InputPasswordFragmentTest.inputPasswordFragment(func: InputPasswordFragmentRobot.() -> Unit) =
    InputPasswordFragmentRobot(mockk(relaxed = true)).apply(func)

class InputPasswordFragmentRobot(private val navController: NavController) {

    infix fun launch(func: InputPasswordFragmentRobot.() -> Unit): InputPasswordFragmentRobot {
        mockToast()

        every {
            navController.navigate(
                InputPasswordFragmentDirections.actionInputPasswordFragmentToInputUsernameFragment(
                    "asdadsadsa",
                    "asdadsadsa",
                    "asdadsadsa",
                    "asdadsadsa",
                    "asdadsadsa",
                    "asdadsadsa", "Dalinar@12"
                )
            )
        } returns Unit

        launchFragmentInContainer<InputPasswordFragment>(
            fragmentArgs = InputPasswordFragmentArgs(
                "asdadsadsa",
                "asdadsadsa",
                "asdadsadsa",
                "asdadsadsa",
                "asdadsadsa",
                "asdadsadsa"
            ).toBundle(),
            themeResId = R.style.RememberAppTheme_Primary
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }

        return apply(func)
    }

    fun inputPassword() {
        R.id.input_password.typeText("Dalinar@12")
    }

    fun clickContinueButton() {
        R.id.input_password_continue.click()
    }

    fun inputConfirmPassword() {
        R.id.input_confirm_password.typeText("Dalinar@12")
    }

    fun inputPasswordAndConfirmPasswordNotEquals() {
        R.id.input_password.typeText("Dalinar@12")
        R.id.input_confirm_password.typeText("Dalinar@11")
    }

    fun inputPasswordAndConfirmPasswordEqualsButNotValid() {
        R.id.input_password.typeText("Dalinar12")
        R.id.input_confirm_password.typeText("Dalinar12")
    }

    fun inputPasswordAndConfirmPasswordEqualsAndValid() {
        R.id.input_password.typeText("Dalinar@12")
        R.id.input_confirm_password.typeText("Dalinar@12")
    }

    infix fun check(func: InputPasswordFragmentResult.() -> Unit) =
        InputPasswordFragmentResult(navController).apply(func)
}

class InputPasswordFragmentResult(private val navController: NavController) {
    fun showRightTexts() {
        R.string.onboarding_input_password_input_name_text.isTextDisplayed()
        R.string.onboarding_input_confirm_password_input_name_text.isTextDisplayed()
        R.string.onboarding_password_hint.isTextDisplayed()
    }

    fun errorMessageInformValues() {
        verify { any<Fragment>().showError(
            R.string.onboarding_input_values, any()
        ) }
    }

    fun errorMessagePasswordsNotEquals() {
        verify { any<Fragment>().showError(
            R.string.onboarding_password_not_equal, any()
        ) }
    }

    fun errorMessagePasswordsInvalid() {
        verify { any<Fragment>().showError(
            R.string.onboarding_password_pattern, any()
        ) }
    }

    fun goToNextScreen() {
        verify {
            navController.navigate(
                InputPasswordFragmentDirections.actionInputPasswordFragmentToInputUsernameFragment(
                    "asdadsadsa",
                    "asdadsadsa",
                    "asdadsadsa",
                    "asdadsadsa",
                    "asdadsadsa",
                    "asdadsadsa", "Dalinar@12"
                )
            )
        }
    }

}