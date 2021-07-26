package com.remember.onboarding.ui

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import com.remember.common.widget.CheckBoxTextView
import com.remember.onboarding.R
import com.remember.onboarding.ui.register.ChooseGenderFragment
import com.remember.onboarding.ui.register.ChooseGenderFragmentArgs
import com.remember.onboarding.ui.register.ChooseGenderFragmentDirections
import com.remember.test.extensions.click
import com.remember.test.extensions.getText
import com.remember.test.extensions.isTextDisplayed
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Description

fun ChooseGenderFragmentTest.chooseGenderFragment(func: ChooseGenderFragmentRobot.() -> Unit) =
    ChooseGenderFragmentRobot(mockk(relaxed = true)).apply(func)

class ChooseGenderFragmentRobot(private val navController: NavController) {

    infix fun launch(func: ChooseGenderFragmentRobot.() -> Unit) : ChooseGenderFragmentRobot {
        every {
            navController.navigate(
                ChooseGenderFragmentDirections.actionChooseGenderFragmentToInputEmailFragment(
                    "hoid",
                    "wit",
                    "05/09/1997",
                    "cellphone",
                    "Women"
                )
            )
        } returns Unit

        launchFragmentInContainer<ChooseGenderFragment>(
            fragmentArgs = ChooseGenderFragmentArgs(
                "hoid",
                "wit",
                "05/09/1997",
                "cellphone"
            ).toBundle(),
            themeResId = R.style.RememberAppTheme_Primary
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun chooseWomanOption() {
        R.id.checkbox_second_option.click()
    }

    fun clickContinueButton() {
        R.id.continue_button.click()
    }

    infix fun check(func: ChooseGenderFragmentResult.() -> Unit) =
        ChooseGenderFragmentResult(navController).apply(func)
}

class ChooseGenderFragmentResult(private val navController: NavController) {

    fun rightTexts() {
        R.string.onboarding_choose_gender_title.isTextDisplayed()
        R.id.checkbox_first_option.onCheckBoxTextViewCheck(
            R.string.onboarding_checkbox_gender_first_option.getText(), "Men", false
        )
        R.id.checkbox_second_option.onCheckBoxTextViewCheck(
            R.string.onboarding_checkbox_gender_second_option.getText(), "Women", false
        )
        R.id.checkbox_third_option.onCheckBoxTextViewCheck(
            R.string.onboarding_checkbox_gender_third_option.getText(), "Transsexual", false
        )
        R.id.checkbox_fourth_option.onCheckBoxTextViewCheck(
            R.string.onboarding_checkbox_gender_fourth_option.getText(), "Non-Binary", false
        )
        R.id.checkbox_firth_option.onCheckBoxTextViewCheck(
            R.string.onboarding_checkbox_gender_firth_option.getText(), "No Gender", false
        )
        R.id.checkbox_six_option.onCheckBoxTextViewCheck(
            R.string.onboarding_checkbox_gender_six_option.getText(), "Other", false
        )
    }

    fun womanOptionSelected() {
        R.id.checkbox_second_option.onCheckBoxTextViewCheck(
            R.string.onboarding_checkbox_gender_second_option.getText(),
            "Women",
            true
        )
    }

    fun goToNextScreen() {
        verify {
            navController.navigate(
                ChooseGenderFragmentDirections.actionChooseGenderFragmentToInputEmailFragment(
                    "hoid",
                    "wit",
                    "05/09/1997",
                    "cellphone",
                    "Women"
                )
            )
        }
    }
}

fun Int.onCheckBoxTextViewCheck(text: String, value: String, isChecked: Boolean) {
    Espresso.onView(ViewMatchers.withId(this)).check(matches(
        CheckBoxTextViewMatcher(text, value, isChecked)
    ))
}

class CheckBoxTextViewMatcher(
    private val text: String, private val value: String, private val isChecked: Boolean
) : BoundedMatcher<View, CheckBoxTextView>(CheckBoxTextView::class.java) {
    override fun describeTo(description: Description) {
        description.appendText("Failed to get item with text: $text \n and")
        description.appendText("Failed to get item with value: $value \n and")
        description.appendText("Failed to get item with isChecked: $isChecked \n")
    }

    override fun matchesSafely(item: CheckBoxTextView?): Boolean {
        if(item == null) return false

        if(item.findViewById<AppCompatTextView>(R.id.text_view).text.toString() != text) return false

        if(item.checkBoxValue != value) return false

        if(item.isChecked != isChecked) return false

        return true
    }

}