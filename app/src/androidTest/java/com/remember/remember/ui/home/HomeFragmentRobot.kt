package com.remember.remember.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.remember.common.extension.showError
import com.remember.remember.R
import com.remember.repository.MemoryLineRepository
import com.remember.repository.ProfileRepository
import com.remember.repository.TypesRepository
import com.remember.repository.model.MemoryLineType
import com.remember.repository.model.MemoryLineTypePagination
import com.remember.repository.model.memoryline.MemoryLine
import com.remember.repository.model.memoryline.MemoryLinePagination
import com.remember.repository.model.memoryline.MomentMemoryLine
import com.remember.repository.model.memoryline.Owner
import com.remember.repository.model.participant.AccountImage
import com.remember.repository.model.participant.Participant
import com.remember.repository.model.profile.Profile
import com.remember.test.extensions.*
import com.remember.test.utils.mockIntent
import com.remember.test.utils.mockNavigationFragment
import com.remember.test.utils.mockResponseRepository
import com.remember.test.utils.mockToast
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.inject

fun HomeFragmentTest.homeFragment(func: HomeFragmentRobot.() -> Unit) =
    HomeFragmentRobot(mockk(relaxed = true)).apply(func)

class HomeFragmentRobot(
    private val navController: NavController
) : KoinComponent {

    private val typesRepository: TypesRepository by inject()
    private val profileRepository: ProfileRepository by inject()

    fun withData() {
        mockResponseRepository(typesRepository::memoryLineTypes).postData(
            MemoryLineTypePagination(
                null,
                listOf(
                    MemoryLineType(
                        "daskldjaskldsa",
                        "daskljdsalkdjas",
                        "Cosmere",
                        2
                    )
                )
            )
        )
    }

    fun withTypesEmpty() {
        mockResponseRepository(typesRepository::memoryLineTypes).postData(
            MemoryLineTypePagination(
                null,
                listOf()
            )
        )
    }

    private fun mockIntents() {
        mockIntent("CREATE_MOMENT")
        mockIntent("CREATE_MEMORY_LINE")
        mockIntent("MEMORY_LINE_TYPE")
        mockIntent("PROFILE")
    }

    private val memoryLineRepository: MemoryLineRepository by inject()

    private val memoryLineData = MemoryLine(
        "Mistborn",
        "aksdjaslkdjsal",
        "çdkçsladksaç",
        Owner(
            "dasda",
            "asddadsa",
            "dasdsa",
            "asdasd"
        ),
        "Cosmere",
        listOf(
            MomentMemoryLine(
                "jdshdsa",
                "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Poppy_14.jpg"
            ),
            MomentMemoryLine(
                "jdsh23",
                "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Poppy_14.jpg"
            )
        ),
        listOf(),
        "2021-02-06T19:23:36.530277-03:00",
        "2021-02-06T19:23:36.530277-03:00"
    )

    fun withDataMemoryLine() {
        mockResponseRepository(memoryLineRepository::memoryLine).postData(
            MemoryLinePagination(
                null,
                null,
                2,
                listOf(memoryLineData)
            )
        )
    }

    fun withMemoryLineThreeMembers() {
        mockResponseRepository(memoryLineRepository::memoryLine).postData(
            MemoryLinePagination(
                null,
                null,
                2,
                listOf(
                    MemoryLine(
                        "Mistborn",
                        "aksdjaslkdjsal",
                        "çdkçsladksaç",
                        Owner(
                            "dasda",
                            "asddadsa",
                            "dasdsa",
                            "asdasd"
                        ),
                        "Cosmere",
                        listOf(
                            MomentMemoryLine(
                                "jdshdsa",
                                "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Poppy_14.jpg"
                            ),
                            MomentMemoryLine(
                                "jdsh23",
                                "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Poppy_14.jpg"
                            )
                        ),
                        listOf(
                            Participant(
                                "123",
                                AccountImage("asd", null),
                                "dsad",
                                "dasdsa"
                            ),
                            Participant(
                                "1234",
                                AccountImage("asd", null),
                                "dsad",
                                "dasdsa"
                            ),
                            Participant(
                                "12345",
                                AccountImage("asd", null),
                                "dsad",
                                "dasdsa"
                            )
                        ),
                        "2021-02-06T19:23:36.530277-03:00",
                        "2021-02-06T19:23:36.530277-03:00"
                    )
                )
            )
        )
    }

    fun withError() {
        mockResponseRepository(memoryLineRepository::memoryLine).postError(Throwable())
    }

    fun withMemoryLineMoments() {
        mockResponseRepository(memoryLineRepository::memoryLine).postData(
            MemoryLinePagination(
                null,
                null,
                23,
                listOf(memoryLineData)
            )
        )
    }

    infix fun launch(func: HomeFragmentRobot.() -> Unit): HomeFragmentRobot {
        mockResponseRepository(profileRepository::informationProfile).postData(
            Profile(
                "id",
                "dasdsa",
                "dsada",
                "dadsa",
                "adasdsa",
                "asdsadsa",
                null,
                "asdadas",
                "asdsadsa"
            )
        )

        every {
            navController.navigate(
                R.id.action_navigation_home_to_memoryLineFragment2,
                any()
            )
        } returns Unit

        every {
            navController.navigate(
                R.id.action_navigation_home_to_memoryLineParticipantsFragment,
                any()
            )
        } returns Unit

        mockNavigationFragment()
        mockToast()
        mockIntents()

        every { NavHostFragment.findNavController(any()) } returns navController

        launchFragmentInContainer<HomeFragment>(
            themeResId = R.style.RememberAppTheme_Primary
        )
        return apply(func)
    }

    fun clickCamera() {
        R.id.back_icon.click(ignoreConstraint = true)
    }

    fun clickAvatar() {
        R.id.avatar_icon.click(ignoreConstraint = true)
    }

    fun clickMore() {
        R.id.home_add_button.click(ignoreConstraint = true)
    }

    fun clickCreateMemoryLine() {
        R.id.select_memory_line.click(ignoreConstraint = true)
    }

    fun clickTypes() {
        R.id.select_memory_line_type.click(ignoreConstraint = true)
    }

    fun clickMembersIcon() {
        R.id.has_more_participants.click()
    }

    fun clickMemoryLineDetail() {
        R.id.enter_memory_line.click()
    }

    fun clickMoments() {
        R.id.first_moment.click()
    }

    infix fun check(func: HomeFragmentCheck.() -> Unit) = HomeFragmentCheck(
        navController
    ).apply(func)

}

class HomeFragmentCheck(private val navController: NavController) {
    fun rightInformation() {
        R.string.app_home_title.isTextDisplayed(targetContext = true)
    }

    fun emptyState() {
        R.id.empty_state_home.hasText(R.string.app_empty_state_home_text.getText(targetContext = true))
    }

    fun openCreateMoment() {
        mockIntent("CREATE_MOMENT")
    }

    fun goToAvatar() {
        mockIntent("PROFILE")
    }

    fun twoOptions() {
        R.id.select_memory_line_type.isEnable()
        R.id.select_memory_line.isEnable()
    }

    fun oneOption() {
        R.id.select_memory_line_type.isEnable()
        R.id.select_memory_line.isDisable()
    }

    fun goToCreateMemoryLine() {
        mockIntent("CREATE_MEMORY_LINE")
    }

    fun goToMemoryLineTypes() {
        mockIntent("MEMORY_LINE_TYPE")
    }

    fun errorMessage() {
        verify { any<Fragment>().showError(
            R.string.app_cannot_open_camera_empty_state, any()
        ) }
    }

    fun rightInformationMemoryLine() {
        "Cosmere".isTextDisplayed()
        R.id.title_memory.hasText("Mistborn")
        R.id.last_time_update.isDisplayed()
        R.id.enter_memory_line.isDisplayed()
    }

    fun membersIcon() {
        R.id.has_more_participants.isDisplayed()
    }

    fun goToParticipants() {
        verify {
            navController.navigate(
                R.id.action_navigation_home_to_memoryLineParticipantsFragment,
                any()
            )
        }
    }

    fun goToMemoryLine() {
        verify {
            navController.navigate(
                R.id.action_navigation_home_to_memoryLineFragment2,
                any()
            )
        }
    }

    fun errorScreen() {
        R.id.error_title.hasText(R.string.common_error_view_title_default.getText())
        R.id.error_text.hasText(R.string.common_memory_lines_error_body.getText())
        R.id.error_button.hasText(R.string.common_try_again.getText())
    }
}
