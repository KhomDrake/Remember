package com.remember.moment.ui.detail

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import com.remember.common.widget.LoaderButton
import com.remember.moment.R
import com.remember.repository.AuthRepository
import com.remember.repository.MomentDetailRepository
import com.remember.repository.model.moment.Moment
import com.remember.repository.model.participant.AccountImage
import com.remember.test.extensions.*
import com.remember.test.utils.mockResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Description
import org.koin.core.KoinComponent
import org.koin.core.inject

fun MomentFragmentTest.momentFragment(func: MomentFragmentRobot.() -> Unit) =
    MomentFragmentRobot(mockk(relaxed = true)).apply(func)

class MomentFragmentRobot(private val navController: NavController) : KoinComponent {

    private val momentDetailRepository: MomentDetailRepository by inject()
    private val authRepository: AuthRepository by inject()

    fun momentDataSuccess() {
        mockResponseRepository(momentDetailRepository::momentDetail)
            .postData(Moment(
                "idMoment",
                AccountImage("owner", null),
                "Moment Test",
                "idMemoryLine",
                "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Poppy_15.jpg",
                10,
                "2021-02-06T19:23:35.036005-03:00",
                "2021-02-06T19:23:35.036005-03:00"
            ))
    }

    fun isNotOwner() {
        every { authRepository.userId() } returns "owner2"
    }

    fun isOwner() {
        every { authRepository.userId() } returns "owner"
    }

    fun momentDataError() {
        mockResponseRepository(momentDetailRepository::momentDetail).postError(Throwable())
    }

    fun removeMoment() {
        mockResponseRepository(momentDetailRepository::deleteMoment).postData(true)
    }

    infix fun launch(func: MomentFragmentRobot.() -> Unit) : MomentFragmentRobot {
        every { navController.navigate(
            MomentFragmentDirections.actionMomentFragmentToMomentCommentsFragment(
                "idMoment"
            )
        ) } returns Unit

        launchFragmentInContainer<MomentFragment>(
            themeResId = R.style.RememberAppTheme_Primary,
            fragmentArgs = MomentFragmentArgs("idMoment").toBundle()
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun clickComments() {
        R.id.comments.click()
    }

    fun clickRemove() {
        R.id.delete_moment.click()
    }

    fun clickConfirm() {
        R.id.negative_button.click()
    }

    fun clickCancel() {
        R.id.positive_button.click()
    }

    infix fun check(func: MomentFragmentResult.() -> Unit) =
        MomentFragmentResult(navController).apply(func)

}

class MomentFragmentResult(private val navController: NavController) : KoinComponent {

    private val momentDetailRepository: MomentDetailRepository by inject()

    fun momentInformation() {
        R.id.moment_image.isDisplayed()
        "Moment Test".isTextDisplayed()
        "10 Comments".isTextDisplayed()
        "Saturday, 06 of february of 2021".isTextDisplayed()
    }

    fun openCommentsFragment() {
        verify {
            navController.navigate(
                MomentFragmentDirections.actionMomentFragmentToMomentCommentsFragment(
                    "idMoment"
                )
            )
        }
    }

    fun removeDisable() {
        R.id.delete_moment.isDisable()
    }

    fun removeEnable() {
        R.id.delete_moment.isEnable()
    }

    fun bottomSheet() {
        R.id.title.hasText(R.string.moment_delete_moment_title.getText())
        R.id.message.hasText(R.string.moment_delete_moment_message.getText())
        R.id.positive_button.hasTextLoaderButton(R.string.moment_delete_moment_positive_button_text.getText())
        R.id.negative_button.hasTextLoaderButton(R.string.moment_delete_moment_negative_button_text.getText())
    }

    fun removeMomentCalled() {
        verify {
            momentDetailRepository.deleteMoment(any())
        }
    }

    fun removeMomentNotCalled() {
        verify(exactly = 0) {
            momentDetailRepository.deleteMoment(any())
        }
    }

    fun errorScreen() {
        R.id.error_title.hasText(R.string.common_error_view_title_default.getText())
        R.id.error_text.hasText(R.string.moment_detail_moment_error_body.getText())
        R.id.error_button.hasText(R.string.common_try_again.getText())
    }
}

fun loaderButtonText(text: String) = object : BoundedMatcher<View, LoaderButton>(
    LoaderButton::class.java) {
    override fun describeTo(description: Description) {
        description.appendText("Checked text $text")
    }

    override fun matchesSafely(item: LoaderButton): Boolean {
        return item.text == text
    }
}

fun Int.hasTextLoaderButton(text: String) {
    Espresso.onView(ViewMatchers.withId(this)).check(ViewAssertions.matches(loaderButtonText(text)))
}
