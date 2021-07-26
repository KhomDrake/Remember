package com.remember.onboarding.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.remember.onboarding.R
import com.remember.onboarding.ui.register.BirthDateFragment
import com.remember.onboarding.ui.register.BirthDateFragmentArgs
import com.remember.onboarding.ui.register.BirthDateFragmentDirections
import com.remember.onboarding.ui.register.OnboardingViewModel
import com.remember.test.extensions.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify

fun BirthDateFragmentTest.birthDateFragment(func: BirthDateFragmentRobot.() -> Unit) =
    BirthDateFragmentRobot(mockk(relaxed = true)).apply(func)

class BirthDateFragmentRobot(private val navController: NavController) {
    private val viewModel = mockk<OnboardingViewModel>(relaxed = true)

    init {
        mockkStatic("com.remember.extension.ViewKt")
    }

    infix fun launch(func: BirthDateFragmentRobot.() -> Unit): BirthDateFragmentRobot {
        every { navController.navigate(
            BirthDateFragmentDirections.actionBirthDateFragmentToInputCellphoneFragment(
                "hoid",
                "with",
                "05/09/1997"
            )
        ) } returns Unit

        launchFragmentInContainer<BirthDateFragment>(
            fragmentArgs = BirthDateFragmentArgs(
                "hoid",
                "with"
            ).toBundle(),
            themeResId = R.style.RememberAppTheme_Dark
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun inputValidBirthDate() {
        R.id.birth_date_input.typeText("05091997")
    }

    fun clickContinueButton() {
        R.id.birth_date_continue.click()
    }

    infix fun check(func: BirthDateFragmentResult.() -> Unit) =
        BirthDateFragmentResult(navController).apply(func)
}

class BirthDateFragmentResult(private val navController: NavController) {
    fun rightText() {
        R.string.onboarding_input_birth_date_title_text.isTextDisplayed()
        R.string.onboarding_date_input_hint.hintDisplayed()
        R.string.onboarding_button_continue_text.isTextDisplayed()
    }

    fun goToNextScreen() {
        verify {
            navController.navigate(
                BirthDateFragmentDirections.actionBirthDateFragmentToInputCellphoneFragment(
                    "hoid",
                    "with",
                    "05/09/1997"
                )
            )
        }
    }
}