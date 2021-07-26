package com.remember.remember.ui.home.memory_line

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import com.remember.common.actions.MEMORY_LINE_ID
import com.remember.common.actions.MEMORY_LINE_NAME
import com.remember.common.actions.OWNER_ID
import com.remember.common.actions.TYPE_MOMENT
import com.remember.common.model.MemoryLineBaseInformation
import com.remember.common.model.MomentType
import com.remember.remember.R
import com.remember.repository.MomentsRepository
import com.remember.repository.model.memoryline.MemoryLineDetail
import com.remember.repository.model.memoryline.Owner
import com.remember.repository.model.moment.Moment
import com.remember.repository.model.moment.MomentsPagination
import com.remember.repository.model.participant.AccountImage
import com.remember.repository.model.participant.ParticipantImage
import com.remember.test.extensions.*
import com.remember.test.utils.checkIntent
import com.remember.test.utils.mockIntent
import com.remember.test.utils.mockResponseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.inject

fun MemoryLineFragmentTest.memoryLineFragment(func: MemoryLineFragmentRobot.() -> Unit) =
    MemoryLineFragmentRobot().apply(func)

class MemoryLineFragmentRobot(
    private val navController: NavController = mockk(relaxed = true)
) : KoinComponent {

    private val momentsRepository: MomentsRepository by inject()
    private val args = MemoryLineFragmentArgs(
        MemoryLineBaseInformation(
            "id",
            "ownerId",
            "Cosmere"
        )
    )

    fun withError() {
        mockResponseRepository(momentsRepository::detailMemoryLine).postData(
            MemoryLineDetail(
                "idMemoryLine",
                "Cosmere",
                "adssada",
                "sadasd",
                "asdasda",
                "asdsadas",
                Owner("ownerId", "asdsa", "dadsada", "dasdas", null),
                listOf()
            )
        )
        mockResponseRepository(momentsRepository::momentsPagination).postError(Throwable())
    }

    fun withDataWithoutParticipants() {
        mockResponseRepository(momentsRepository::detailMemoryLine).postData(
            MemoryLineDetail(
                "idMemoryLine",
                "Cosmere",
                "adssada",
                "sadasd",
                "asdasda",
                "asdsadas",
                Owner("ownerId", "asdsa", "dadsada", "dasdas", null),
                listOf()
            )
        )
        mockResponseRepository(momentsRepository::momentsPagination).postData(
            MomentsPagination(
                null,
                null,
                10,
                listOf(
                    Moment(
                        "Mistborn",
                        AccountImage("Kelsier"),
                        "The hero of ages",
                        "idMemoryLine",
                        "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Poppy_14.jpg",
                        10,
                        "2021-02-06T19:23:35.036005-03:00",
                        "2021-02-06T19:23:35.036005-03:00"
                    )
                )
            )
        )
    }

    fun withData() {
        mockResponseRepository(momentsRepository::detailMemoryLine).postData(
            MemoryLineDetail(
                "idMemoryLine",
                "Cosmere",
                "adssada",
                "sadasd",
                "asdasda",
                "asdsadas",
                Owner("ownerId", "asdsa", "dadsada", "dasdas", null),
                listOf(
                    ParticipantImage(
                        "asdas1",
                        "asdad"
                    ),
                    ParticipantImage(
                        "asdas2",
                        "asdad"
                    ),
                    ParticipantImage(
                        "asdas3",
                        "asdad"
                    )
                )
            )
        )
        mockResponseRepository(momentsRepository::momentsPagination).postData(
            MomentsPagination(
                null,
                null,
                10,
                listOf(
                    Moment(
                        "Mistborn",
                        AccountImage("Kelsier"),
                        "The hero of ages",
                        "idMemoryLine",
                        "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Poppy_14.jpg",
                        10,
                        "2021-02-06T19:23:35.036005-03:00",
                        "2021-02-06T19:23:35.036005-03:00"
                    )
                )
            )
        )
    }

    private fun mockIntents() {
        mockIntent("PROFILE", true)
    }

    private fun mockNavController() {
        every {
            navController.navigate(
                MemoryLineFragmentDirections.actionMemoryLineFragmentToMemoryLineConfigurationFragment(
                    args.memoryLineData
                )
            )
        } returns Unit

        every {
            navController.navigate(
                MemoryLineFragmentDirections.actionMemoryLineFragmentToMemoryLineParticipantsFragment(
                    args.memoryLineData.idMemoryLine
                )
            )
        } returns Unit
    }

    infix fun launch(func: MemoryLineFragmentRobot.() -> Unit): MemoryLineFragmentRobot {

        mockNavController()
        mockIntents()
        launchFragmentInContainer<MemoryLineFragment>(
            themeResId = R.style.RememberAppTheme_Light,
            fragmentArgs = args.toBundle()
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun clickCamera() {
        mockIntent(
            "CREATE_MOMENT",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(TYPE_MOMENT)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MomentType.CAMERA.name)),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("id"))
        )
        R.id.back_icon.click()
    }

    fun clickAvatar() {
        R.id.avatar_icon.click()
    }

    fun clickConfig() {
        R.id.configuration.click()
    }

    fun clickAddMoment() {
        R.id.add_moment.click(ignoreConstraint = true)
    }

    fun clickSecondOption() {
        mockIntent(
            "CREATE_MOMENT",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(TYPE_MOMENT)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MomentType.CAMERA.name)),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("id")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_NAME)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("Cosmere")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(OWNER_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("ownerId"))
        )
        R.id.select_camera.click(ignoreConstraint = true)
    }

    fun clickFirstOption() {
        mockIntent(
            "CREATE_MOMENT",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(TYPE_MOMENT)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MomentType.GALLERY.name)),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("id")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_NAME)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("Cosmere")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(OWNER_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("ownerId"))
        )
        R.id.select_gallery.click(ignoreConstraint = true)
    }

    fun clickParticipantsIcon() {
        R.id.has_more_participants.click()
    }

    infix fun check(func: MemoryLineFragmentCheck.() -> Unit) = MemoryLineFragmentCheck(
        navController,
        args.memoryLineData
    ).apply(func)

}

class MemoryLineFragmentCheck(
    private val navController: NavController,
    private val memoryLineBaseInformation: MemoryLineBaseInformation
) {
    fun rightInformation() {
        R.id.title.isDisplayed()
        R.id.title.hasText(R.string.app_home_title.getText(true))
        R.id.configuration.isDisplayed()
        R.id.has_more_participants.isDisplayed()
        R.id.name_memory_line.isDisplayed()
        R.id.name_memory_line.hasText("Cosmere")
        "The hero of ages".isTextDisplayed()
        "Saturday, 06 of february of 2021".isTextDisplayed()
        "Comments".isTextDisplayed()
    }

    fun goToCameraWithIdMemoryLine() {
        checkIntent(
            "CREATE_MOMENT",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(TYPE_MOMENT)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MomentType.CAMERA.name)),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("id")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_NAME)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("Cosmere")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(OWNER_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("ownerId"))
        )
    }

    fun goToProfile() {
        checkIntent("PROFILE", true)
    }

    fun goToConfigurationMemoryLine() {
        every {
            navController.navigate(
                MemoryLineFragmentDirections.actionMemoryLineFragmentToMemoryLineConfigurationFragment(
                    memoryLineBaseInformation
                )
            )
        } returns Unit
    }

    fun twoOptions() {
        R.id.select_gallery.isEnable()
        R.id.select_camera.isEnable()
    }

    fun goToGallery() {
        checkIntent(
            "CREATE_MOMENT",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(TYPE_MOMENT)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MomentType.GALLERY.name)),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("id")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_NAME)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("Cosmere")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(OWNER_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("ownerId"))
        )
    }

    fun goToCamera() {
        checkIntent(
            "CREATE_MOMENT",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(TYPE_MOMENT)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(MomentType.CAMERA.name)),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("id")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(MEMORY_LINE_NAME)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("Cosmere")),
            IntentMatchers.hasExtras(BundleMatchers.hasKey(OWNER_ID)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue("ownerId"))
        )
    }

    fun errorScreen() {
        R.id.error_title.hasText(R.string.app_error_memory_line_title.getText(true))
        R.id.error_text.hasText(R.string.app_error_memory_line_body.getText(true))
        R.id.error_button.hasText(R.string.common_try_again.getText(true))
    }

    fun participantsIconNotDisplayed() {
        R.id.has_more_participants.isNotDisplayed()
    }

    fun participantsIconDisplayed() {
        R.id.has_more_participants.isDisplayed()
    }

    fun goToParticipants() {
        verify {
            navController.navigate(
                MemoryLineFragmentDirections.actionMemoryLineFragmentToMemoryLineParticipantsFragment(
                    memoryLineBaseInformation.idMemoryLine
                )
            )
        }
    }

}