package com.remember.remember.ui.home.memory_line.configuration

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.remember.common.model.MemoryLineBaseInformation
import com.remember.common.widget.ImageTextView
import com.remember.common.widget.LoaderButton
import com.remember.remember.R
import com.remember.repository.AuthRepository
import com.remember.repository.MemoryLineConfigurationRepository
import com.remember.repository.model.account.Account
import com.remember.repository.model.participant.ParticipantFull
import com.remember.repository.model.participant.ParticipantPagination
import com.remember.test.extensions.*
import com.remember.test.utils.mockResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Description
import org.koin.core.KoinComponent
import org.koin.core.inject

fun MemoryLineConfigurationFragmentTest.memoryLineConfiguration(func: MemoryLineConfigurationFragmentRobot.() -> Unit) =
    MemoryLineConfigurationFragmentRobot().apply(func)

class MemoryLineConfigurationFragmentRobot(
    private val navController: NavController = mockk(relaxed = true)
) : KoinComponent {

    private val repository: MemoryLineConfigurationRepository by inject()
    private val authRepository: AuthRepository by inject()

    private val args = MemoryLineConfigurationFragmentArgs(
        MemoryLineBaseInformation(
            "id",
            "ownerId",
            "Cosmere"
        )
    )

    fun isOwner() {
        every { authRepository.userId() } returns "ownerId"
    }

    fun isNotOwner() {
        every { authRepository.userId() } returns "ownerId2"
    }

    private fun mockRepository() {
        mockResponseRepository(repository::memoryLineParticipants).postData(
            ParticipantPagination(
                10,
                null,
                null,
                listOf(
                    ParticipantFull(
                        "asdasda",
                        Account(
                            "asdsad",
                            "",
                            "",
                            "",
                            ""
                        ),
                        "",
                        "",
                        false
                    ),
                    ParticipantFull(
                        "asdasda",
                        Account(
                            "asdsad",
                            "",
                            "",
                            "",
                            ""
                        ),
                        "",
                        "",
                        false
                    ),
                    ParticipantFull(
                        "asdasda",
                        Account(
                            "asdsad",
                            "",
                            "",
                            "",
                            ""
                        ),
                        "",
                        "",
                        false
                    )
                )
            )
        )

        mockResponseRepository(repository::deleteMemoryLine).postData(true)
        mockResponseRepository(repository::updateMemoryLine).postData(mockk(relaxed = true))
    }

    private fun mockNavController() {
        every {
            navController.navigate(
                MemoryLineConfigurationFragmentDirections.actionMemoryLineConfigurationFragmentToMemoryLineParticipantsFragment(
                    args.memoryLineData.idMemoryLine
                )
            )
        } returns Unit

        every {
            navController.navigate(
                MemoryLineConfigurationFragmentDirections.actionMemoryLineConfigurationFragmentToMemoryLineAddParticipantFragment(
                    args.memoryLineData.idMemoryLine
                )
            )
        } returns Unit
    }

    infix fun launch(func: MemoryLineConfigurationFragmentRobot.() -> Unit): MemoryLineConfigurationFragmentRobot {
        mockRepository()
        mockNavController()
        launchFragmentInContainer<MemoryLineConfigurationFragment>(
            fragmentArgs = args.toBundle(),
            themeResId = R.style.RememberAppTheme_Light
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun clickDeleteMemoryLine() {
        R.id.delete_memory_line.click()
    }

    fun clickCancel() {
        R.id.positive_button.click()
    }

    fun clickConfirm() {
        R.id.negative_button.click()
    }

    fun clickAddNewParticipants() {
        R.id.add_participant.click()
    }

    fun clickParticipants() {
        R.id.quantity_participants.click()
    }

    fun clickEditName() {
        R.id.edit_memory_line_name.click()
    }

    infix fun check(func: MemoryLineConfigurationFragmentCheck.() -> Unit) = MemoryLineConfigurationFragmentCheck(
        navController, args.memoryLineData
    ).apply(func)

}

class MemoryLineConfigurationFragmentCheck(
    private val navController: NavController,
    private val information: MemoryLineBaseInformation
) : KoinComponent {

    private val repository: MemoryLineConfigurationRepository by inject()

    fun rightInformation() {
        R.id.memory_line_name.hasText("Cosmere")
        R.id.add_participant.imageTextViewText(
            R.string.app_memory_line_configuration_add_participant.getText(true)
        )
        R.id.delete_memory_line.imageTextViewText(
            R.string.app_memory_line_configuration_delete_memory_line_text.getText(true)
        )
        R.id.quantity_participants.imageTextViewText(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(
                R.string.app_quantity_participants,
                3
            )
        )
    }

    fun deleteMemoryLineEnable() {
        R.id.delete_memory_line.isDisplayed()
    }

    fun deleteMemoryLineDisable() {
        R.id.delete_memory_line.isNotDisplayed()
    }

    fun bottomSheetDeleteMemoryLine() {
        R.string.app_delete_memory_line_title.getText(true).isTextDisplayed()
        R.string.app_delete_memory_line_message.getText(true).isTextDisplayed()
        R.id.positive_button.hasTextLoaderButton(
            R.string.app_delete_memory_line_positive_button.getText(true)
        )
        R.id.negative_button.hasTextLoaderButton(
            R.string.app_delete_memory_line_negative_button.getText(true)
        )
    }

    fun notCalledDeleteMemoryLine() {
        verify(exactly = 0) {
            repository.deleteMemoryLine(any())
        }
    }

    fun calledDeleteMemoryLine() {
        verify {
            repository.deleteMemoryLine(any())
        }
    }

    fun goToAddParticipants() {
        every {
            navController.navigate(
                MemoryLineConfigurationFragmentDirections.actionMemoryLineConfigurationFragmentToMemoryLineAddParticipantFragment(
                    information.idMemoryLine
                )
            )
        } returns Unit
    }

    fun goToParticipants() {
        every {
            navController.navigate(
                MemoryLineConfigurationFragmentDirections.actionMemoryLineConfigurationFragmentToMemoryLineParticipantsFragment(
                    information.idMemoryLine
                )
            )
        } returns Unit
    }

    fun bottomSheetEditName() {
        R.string.app_change_memory_line_name_title.getText(true).isTextDisplayed()
        R.string.app_change_memory_line_name_hint.getText(true).isHintDisplayed()
        R.id.positive_button.hasTextLoaderButton(R.string.app_change_memory_line_name_positive_button.getText(true))
        R.id.negative_button.hasTextLoaderButton(R.string.app_change_memory_line_name_negative_button.getText(true))
    }

    fun notCalledUpdateName() {
        verify(exactly = 0) {
            repository.updateMemoryLine(any(), any())
        }
    }
}

fun Int.imageTextViewText(text: String) =
    onView(withId(this)).check(matches(MatcherImageTextView(text)))

class MatcherImageTextView(private val text: String)
    : BoundedMatcher<View, ImageTextView>(ImageTextView::class.java) {
    override fun describeTo(description: Description?) {
        description?.appendValue("Failed to found text $text")
    }

    override fun matchesSafely(item: ImageTextView): Boolean {
        val textView = item.findViewById<AppCompatTextView>(R.id.text)
        return textView.text.toString() == text
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
    onView(withId(this)).check(matches(loaderButtonText(text)))
}
