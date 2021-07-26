package com.remember.remember.ui.home.memory_line.participants

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.remember.R
import com.remember.repository.InviteRepository
import com.remember.repository.SearchRepository
import com.remember.repository.model.search.SearchPagination
import com.remember.repository.model.search.UserSearch
import com.remember.test.extensions.*
import com.remember.test.utils.mockResponseRepository
import com.remember.test.utils.mockToast
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.inject

fun MemoryLineAddParticipantFragmentTest.memoryLineAddParticipants(func: MemoryLineAddParticipantFragmentRobot.() -> Unit) =
    MemoryLineAddParticipantFragmentRobot().apply(func)

class MemoryLineAddParticipantFragmentRobot : KoinComponent {

    private val inviteRepository: InviteRepository by inject()
    private val searchRepository: SearchRepository by inject()

    private val args = MemoryLineAddParticipantFragmentArgs(
        "idMemoryLine"
    )

    fun withAddParticipantsWithSuccess() {
        mockResponseRepository(inviteRepository::createInvites).postData(
            true
        )
    }

    fun withSearchUsers() {
        mockResponseRepository(searchRepository::searchProfile).postData(
            SearchPagination(
                null,
                null,
                2,
                listOf(
                    UserSearch(
                        "id",
                        "kaladin",
                        "Stormblessed",
                        null
                    ),
                    UserSearch(
                        "id",
                        "kelsier",
                        "Survivor",
                        null
                    )
                )
            )
        )
    }

    fun withAddParticipantsError() {
        mockResponseRepository(inviteRepository::createInvites).postError(
            Throwable()
        )
    }

    infix fun launch(func: MemoryLineAddParticipantFragmentRobot.() -> Unit): MemoryLineAddParticipantFragmentRobot {
        val navController = mockk<NavController>(relaxed = true)
        every { navController.navigateUp() } returns false
        mockToast()
        launchFragmentInContainer<MemoryLineAddParticipantFragment>(
            fragmentArgs = args.toBundle(),
            themeResId = R.style.RememberAppTheme_Light
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun clickAddParticipantsButton() {
        R.id.confirm.click(ignoreConstraint = true)
    }

    fun inputInvalidUser() {
        R.id.edit_text.typeText("wo")
    }

    fun inputValidUser() {
        R.id.edit_text.typeText("Vin")
    }

    fun clickSearchIcon() {
        R.id.icon.click()
    }

    fun clickFirstSearchedUsers() {
        R.id.users.clickOnRecyclerViewItem(0)
    }

    fun clickToRemoveFirstSearchedUsers() {
        R.id.users_added.clickOnRecyclerViewInsideItem(0, R.id.remove_participant)
    }

    infix fun check(func: MemoryLineAddParticipantFragmentCheck.() -> Unit) = MemoryLineAddParticipantFragmentCheck().apply(func)
}

class MemoryLineAddParticipantFragmentCheck : KoinComponent {

    private val searchRepository: SearchRepository by inject()

    fun errorMessageNoParticipantsSelected() {
        verify {
            any<Fragment>().showError(
                R.string.app_add_participant_not_user_selected,
                any()
            )
        }
    }

    fun searchIconNotDisplayed() {
        R.id.icon.isNotDisplayed()
    }

    fun searchIconDisplayed() {
        R.id.icon.isDisplayed()
    }

    fun loadUsersCalled() {
        verify {
            searchRepository.searchProfile(any())
        }
    }

    fun oneUserSelected() {
        R.id.users_added.checkRecyclerViewQuantityOfItems(1)
    }

    fun hasNoUserSelected() {
        R.id.users_added.checkRecyclerViewQuantityOfItems(0)
    }

    fun errorMessageAddParticipants() {
        verify {
            any<Fragment>().showError(
                R.string.app_failure_to_create_invite,
                any()
            )
        }
    }

    fun successMessageAddParticipants() {
        verify {
            any<Fragment>().showSuccess(
                R.string.app_successfully_invited,
                any()
            )
        }
    }

    fun usersLoaded() {
        "kaladin".isTextDisplayed()
        "kelsier".isTextDisplayed()
    }

}