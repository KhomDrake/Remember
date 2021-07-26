package com.remember.onboarding.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.remember.common.extension.showError
import com.remember.onboarding.R
import com.remember.onboarding.ui.register.InputCellphoneFragment
import com.remember.onboarding.ui.register.InputCellphoneFragmentArgs
import com.remember.onboarding.ui.register.InputCellphoneFragmentDirections
import com.remember.test.extensions.*
import com.remember.test.utils.mockToast
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

fun InputCellphoneFragmentTest.inputCellphoneFragment(func: InputCellphoneFragmentRobot.() -> Unit) =
    InputCellphoneFragmentRobot(mockk(relaxed = true)).apply(func)

class InputCellphoneFragmentRobot(private val navController: NavController) {

    infix fun launch(func: InputCellphoneFragmentRobot.() -> Unit): InputCellphoneFragmentRobot {
        every {
            navController.navigate(
                InputCellphoneFragmentDirections.actionInputCellphoneFragmentToChooseGenderFragment(
                    "hoid",
                    "wit",
                    "05/09/1997",
                    "+5511913162330"
                )
            )
        } returns Unit

        mockToast()

        launchFragmentInContainer<InputCellphoneFragment>(
            fragmentArgs = InputCellphoneFragmentArgs(
                "hoid",
                "wit",
                "05/09/1997"
            ).toBundle(),
            themeResId = R.style.RememberAppTheme_Primary
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun inputDDD() {
        R.id.ddd_input.typeText("11")
    }

    fun inputWrongDDD() {
        R.id.ddd_input.typeText("00")
    }

    fun inputNumber() {
        R.id.number_input.typeText("913162330")
    }

    fun inputWrongNumber() {
        R.id.number_input.typeText("91316233")
    }

    fun clickContinueButton() {
        R.id.continue_button.click()
    }

    infix fun check(func: InputCellphoneFragmentResult.() -> Unit) =
        InputCellphoneFragmentResult(navController).apply(func)
}

class InputCellphoneFragmentResult(private val navController: NavController) {
    fun rightTexts() {
        R.string.onboarding_input_cellphone_title.isTextDisplayed()
    }

    fun dddAndNumber() {
        R.id.ddd_input.hasText("11")
        R.id.number_input.hasText("91316-2330")
    }

    fun goToNextScreen() {
        verify {
            navController.navigate(
                InputCellphoneFragmentDirections.actionInputCellphoneFragmentToChooseGenderFragment(
                    "hoid",
                    "wit",
                    "05/09/1997",
                    "+551191316-2330"
                )
            )
        }
    }

    fun errorMessageNumber() {
        verify { any<Fragment>().showError(
            R.string.onboarding_phone_number_invalid, any()
        ) }
    }

    fun errorMessageDDD() {
        verify { any<Fragment>().showError(
            R.string.onboarding_ddd_invalid, any()
        ) }
    }
}