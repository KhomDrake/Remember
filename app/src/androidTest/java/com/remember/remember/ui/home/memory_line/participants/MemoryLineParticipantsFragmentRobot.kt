package com.remember.remember.ui.home.memory_line.participants

import androidx.fragment.app.testing.launchFragmentInContainer
import com.remember.remember.R
import com.remember.repository.MemoryLineRepository
import com.remember.repository.model.account.Account
import com.remember.repository.model.participant.ParticipantFull
import com.remember.repository.model.participant.ParticipantPagination
import com.remember.test.extensions.*
import com.remember.test.utils.mockResponseRepository
import org.koin.core.KoinComponent
import org.koin.core.inject


fun MemoryLineParticipantsFragmentTest.memoryParticipantsFragment(func: MemoryLineParticipantsFragmentRobot.() -> Unit) =
    MemoryLineParticipantsFragmentRobot().apply(func)

class MemoryLineParticipantsFragmentRobot : KoinComponent {

    private val args = MemoryLineParticipantsFragmentArgs(
        "idMemoryLine"
    )

    private val memoryLineRepository: MemoryLineRepository by inject()

    fun firstIsOwner() {
        mockResponseRepository(memoryLineRepository::memoryLineParticipants).postData(
            ParticipantPagination(
                0,
                null,
                null,
                listOf(
                    ParticipantFull(
                        "asdasda",
                        Account(
                            "asdsad",
                            null,
                            "Kaladin",
                            "Stormblessed",
                            "Stormblessed"
                        ),
                        "",
                        "",
                        true
                    )
                )
            )
        )
    }

    fun firstIsNotOwner() {
        mockResponseRepository(memoryLineRepository::memoryLineParticipants).postData(
            ParticipantPagination(
                0,
                null,
                null,
                listOf(
                    ParticipantFull(
                        "asdasda",
                        Account(
                            "asdsad",
                            null,
                            "Kaladin",
                            "Stormblessed",
                            "Stormblessed"
                        ),
                        "",
                        "",
                        false
                    )
                )
            )
        )
    }

    fun noParticipants() {
        mockResponseRepository(memoryLineRepository::memoryLineParticipants).postData(
            ParticipantPagination(
                0,
                null,
                null,
                listOf()
            )
        )
    }

    fun withError() {
        mockResponseRepository(memoryLineRepository::memoryLineParticipants).postError(Throwable())
    }

    infix fun launch(func: MemoryLineParticipantsFragmentRobot.() -> Unit): MemoryLineParticipantsFragmentRobot {
        launchFragmentInContainer<MemoryLineParticipantsFragment>(
            fragmentArgs = args.toBundle(),
            themeResId = R.style.RememberAppTheme_Light
        )
        return apply(func)
    }

    infix fun check(func: MemoryLineParticipantsFragmentCheck.() -> Unit) = MemoryLineParticipantsFragmentCheck().apply(func)
}

class MemoryLineParticipantsFragmentCheck {
    fun firstIsOwner() {
        R.id.participant_name.hasText("Stormblessed")
        R.id.owner_memory_line.isDisplayed()
        R.id.owner_memory_line.hasText(R.string.app_participant_item_memory_line_owner.getText(true))
    }

    fun firstIsNotOwner() {
        R.id.participant_name.hasText("Stormblessed")
        R.id.owner_memory_line.isNotDisplayed()
    }

    fun emptyState() {
        R.id.no_participant.hasText(R.string.app_memory_line_participants_empty_state_text.getText(true))
    }

    fun errorState() {
        R.id.error_title.hasText(R.string.common_error_view_title_default.getText(true))
        R.id.error_text.hasText(R.string.common_error_view_body_default.getText(true))
        R.id.error_button.hasText(R.string.common_try_again.getText(true))
    }
}