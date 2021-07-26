package com.remember.moment.ui.select_memory_line

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import com.remember.common.ui.memory_line.MemoryLineViewModel
import com.remember.moment.R
import com.remember.moment.ui.create.select_memory_line.ChooseMemoryLineFragment
import com.remember.moment.ui.create.select_memory_line.ChooseMemoryLineFragmentArgs
import com.remember.repository.TypesRepository
import com.remember.repository.model.MemoryLineType
import com.remember.repository.model.MemoryLineTypePagination
import com.remember.test.extensions.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import org.koin.dsl.module

fun ChooseMemoryLineFragmentTest.chooseMemoryLineFragment(func: ChooseMemoryLineFragmentRobot.() -> Unit) =
    ChooseMemoryLineFragmentRobot(mockk(relaxed = true)).apply(func)

class ChooseMemoryLineFragmentRobot(private val navController: NavController) : KoinComponent {

    private val typesRepository: TypesRepository by inject()

    fun typesSuccess() {
        every {
            typesRepository.memoryLineTypes(any())
        } returns MutableResponseLiveData<MemoryLineTypePagination>().apply {
            postData(
                MemoryLineTypePagination(
                    null,
                    listOf(
                        MemoryLineType(
                            "dalkjsdas",
                            "lkdjaslkdjsal",
                            "Memory Line Test",
                            1
                        )
                    )
                )
            )
        }
    }

    fun typesFailure() {
        every {
            typesRepository.memoryLineTypes(any())
        } returns MutableResponseLiveData<MemoryLineTypePagination>().apply {
            postError(Throwable())
        }
    }

    fun typesLoading() {
        every {
            typesRepository.memoryLineTypes(any())
        } returns MutableResponseLiveData<MemoryLineTypePagination>().apply {
            postLoading()
        }
    }

    infix fun launch(func: ChooseMemoryLineFragmentRobot.() -> Unit) : ChooseMemoryLineFragmentRobot {

        every { navController.navigateUp() } returns true
        loadKoinModules(module(override = true) {
            viewModel { mockk<MemoryLineViewModel>(relaxed = true) }
        })

        launchFragmentInContainer<ChooseMemoryLineFragment>(
            fragmentArgs = ChooseMemoryLineFragmentArgs(
                mockk(relaxed = true)
            ).toBundle(),
            themeResId = R.style.RememberAppTheme_Primary
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun clickTryAgain() {
        R.id.error_button.click()
    }

    fun clickBack() {
        R.id.back_icon.click()
    }

    infix fun check(func: ChooseMemoryLineFragmentResult.() -> Unit) =
        ChooseMemoryLineFragmentResult(mockk(relaxed = true)).apply(func)
}

class ChooseMemoryLineFragmentResult(private val navController: NavController) : KoinComponent {

    private val typesRepository: TypesRepository by inject()

    fun rightInformation() {
        R.string.moment_choose_memory_line_text.isTextDisplayed()
        "Memory Line Test".isTextDisplayed()
        R.id.page_indicator_view.isDisplayed()
    }

    fun errorScreen() {
        R.id.error_title.hasText(R.string.common_error_view_title_default.getText())
        R.id.error_text.hasText(R.string.moment_types_error_body.getText())
        R.id.error_button.hasText(R.string.common_try_again.getText())
    }

    fun typeRequestedAgain() {
        verify(exactly = 2) {
            typesRepository.memoryLineTypes(any())
        }
    }

    fun loadingScreen() {
        R.id.shimmer_home.isDisplayed()
    }

    fun navigateUp() {
        verify { navController.navigateUp() }
    }
}