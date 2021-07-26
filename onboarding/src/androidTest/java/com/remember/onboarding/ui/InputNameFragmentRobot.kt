package com.remember.onboarding.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.remember.common.extension.showError
import com.remember.onboarding.R
import com.remember.onboarding.ui.register.InputNameFragment
import com.remember.onboarding.ui.register.InputNameFragmentDirections
import com.remember.test.extensions.*
import com.remember.test.utils.mockToast
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

fun InputNameFragmentTest.inputNameFragment(func: InputNameFragmentRobot.() -> Unit) =
    InputNameFragmentRobot(mockk(relaxed = true)).apply(func)

class InputNameFragmentRobot(private val navController: NavController) {

    infix fun launch(func: InputNameFragmentRobot.() -> Unit): InputNameFragmentRobot {
        mockToast()

        every { navController.navigate(
            InputNameFragmentDirections.actionInputNameFragmentToBirthDateFragment(
                "Dalinar Kholin",
                "Blackthorn"
            )
        ) } returns Unit

        launchFragmentInContainer<InputNameFragment>(
            themeResId = R.style.RememberAppTheme_Primary
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }

        return apply(func)
    }

    fun inputName() {
        R.id.input_name_input.typeText("Dalinar Kholin")
    }

    fun clickContinueButton() {
        R.id.continue_button.click()
    }

    fun inputNickname() {
        R.id.input_nickname_input.typeText("Blackthorn")
    }

    infix fun check(func: InputNameFragmentResult.() -> Unit) =
        InputNameFragmentResult(navController).apply(func)

}

class InputNameFragmentResult(private val navController: NavController) {
    fun showRightTexts() {
        R.string.onboarding_input_first_name_input_name_text.isTextDisplayed()
        R.string.onboarding_input_second_name_input_name_text.isTextDisplayed()
    }

    fun errorMessage() {
        verify { any<Fragment>().showError(
            R.string.onboarding_input_values, any()
        ) }
    }

    fun goToNextScreen() {
        verify { navController.navigate(
            InputNameFragmentDirections.actionInputNameFragmentToBirthDateFragment(
                "Dalinar Kholin",
                "Blackthorn"
            )
        ) }
    }
}