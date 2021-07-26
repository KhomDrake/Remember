package com.remember.onboarding.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import com.remember.common.extension.showError
import com.remember.onboarding.R
import com.remember.onboarding.ui.register.InputEmailFragment
import com.remember.onboarding.ui.register.InputEmailFragmentArgs
import com.remember.onboarding.ui.register.InputEmailFragmentDirections
import com.remember.repository.AuthRepository
import com.remember.test.extensions.*
import com.remember.test.utils.mockToast
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.inject

fun InputEmailFragmentTest.inputEmailFragment(func: InputEmailFragmentRobot.() -> Unit) =
    InputEmailFragmentRobot(mockk(relaxed = true)).apply(func)

class InputEmailFragmentRobot(private val navController: NavController) : KoinComponent {

    private val repository: AuthRepository by inject()

    fun verifyEmailSucceed() {
        every { repository.verifyEmail(any()) } returns MutableResponseLiveData<Boolean>().apply {
            postData(true)
        }
    }

    fun verifyEmailFail() {
        every { repository.verifyEmail(any()) } returns MutableResponseLiveData<Boolean>().apply {
            postError(Throwable())
        }
    }

    infix fun launch(func: InputEmailFragmentRobot.() -> Unit): InputEmailFragmentRobot {
        mockToast()

        every { navController.navigate(
            InputEmailFragmentDirections.actionInputEmailFragmentToInputPasswordFragment(
                "asdasda",
                "asdasda",
                "asdasda",
                "asdasda",
                "asdasda",
                "viniciushotmail.com"
            )
        ) } returns Unit

        launchFragmentInContainer<InputEmailFragment>(
            fragmentArgs = InputEmailFragmentArgs(
                "asdasda",
                "asdasda",
                "asdasda",
                "asdasda",
                "asdasda"
            ).toBundle(),
            themeResId = R.style.RememberAppTheme_Primary
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun inputInvalidEmail() {
        R.id.input_email_input.typeText("viniciushotmail.com")
    }

    fun clickContinueButton() {
        R.id.continue_button.click()
    }

    fun inputValidEmail() {
        R.id.input_email_input.typeText("vinicius@hotmail.com")
    }

    infix fun check(func: InputEmailFragmentResult.() -> Unit) =
        InputEmailFragmentResult(navController).apply(func)
}

class InputEmailFragmentResult(private val navController: NavController) {
    fun showRightText() {
        R.string.onboarding_input_email_title.isTextDisplayed()
    }

    fun errorMessage() {
        verify { any<Fragment>().showError(
            R.string.onboarding_invalid_email, any()
        ) }
    }

    fun goToNextScreen() {
        verify {
            navController.navigate(
                InputEmailFragmentDirections.actionInputEmailFragmentToInputPasswordFragment(
                    "asdasda",
                    "asdasda",
                    "asdasda",
                    "asdasda",
                    "asdasda",
                    "vinicius@hotmail.com"
                )
            )
        }
    }

}