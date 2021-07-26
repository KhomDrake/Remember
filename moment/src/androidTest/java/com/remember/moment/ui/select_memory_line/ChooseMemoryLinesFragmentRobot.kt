package com.remember.moment.ui.select_memory_line

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import com.remember.common.model.MemoryLineBaseInformation
import com.remember.common.ui.memory_line.MemoryLineViewModel
import com.remember.moment.R
import com.remember.moment.ui.create.select_memory_line.ChooseMemoryLineFragmentDirections
import com.remember.moment.ui.create.select_memory_line.adapter.ChooseMemoryLinesFragment
import com.remember.repository.MemoryLineRepository
import com.remember.repository.model.memoryline.MemoryLine
import com.remember.repository.model.memoryline.MemoryLinePagination
import com.remember.repository.model.memoryline.Owner
import com.remember.test.extensions.click
import com.remember.test.extensions.hasText
import com.remember.test.extensions.isDisplayed
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import org.koin.dsl.module
import java.io.File

fun ChooseMemoryLinesFragmentTest.chooseMemoryLinesFragment(func: ChooseMemoryLinesFragmentRobot.() -> Unit) =
    ChooseMemoryLinesFragmentRobot(mockk(relaxed = true)).apply(func)

class ChooseMemoryLinesFragmentRobot(private val navController: NavController) : KoinComponent {

    private val file: File = mockk(relaxed = true)
    private val memoryLineRepository: MemoryLineRepository by inject()

    fun memoryLineSuccess() {
        every {
            memoryLineRepository.memoryLine(any(), any())
        } returns MutableResponseLiveData<MemoryLinePagination>().apply {
            postData(
                MemoryLinePagination(
                    null,
                    null,
                    1,
                    listOf(
                        MemoryLine(
                            "Mistborn",
                            "idMemoryLine",
                            "Descrição",
                            Owner(
                                "idOwner",
                                "",
                                "",
                                "",
                                null
                            ),
                            "Type",
                            listOf(),
                            listOf(),
                            "2021-02-06T19:23:36.530277-03:00",
                            "2021-02-06T19:23:36.530277-03:00"
                        )
                    )
                )
            )
        }
    }

    infix fun launch(func: ChooseMemoryLinesFragmentRobot.() -> Unit): ChooseMemoryLinesFragmentRobot {
        val viewModel = MemoryLineViewModel(memoryLineRepository)
        loadKoinModules(module(override = true) {
            viewModel { viewModel }
        })

        every { navController.navigate(
            ChooseMemoryLineFragmentDirections.sendToBucket(
                file,
                MemoryLineBaseInformation(
                "idMemoryLine",
                "idOwner",
                "Mistborn"
                )
            )
        ) } returns Unit

        launchFragmentInContainer<ChooseMemoryLinesFragment>(
            themeResId = R.style.RememberAppTheme_Primary,
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ChooseMemoryLinesFragment(file)
                }
            }
        ).onFragment {
            Navigation.setViewNavController((it as Fragment).requireView(), navController)
        }
        return apply(func)
    }

    fun clickFirstMemoryLine() {
        R.id.enter_memory_line.click()
    }

    infix fun check(func: ChooseMemoryLinesFragmentResult.() -> Unit) =
        ChooseMemoryLinesFragmentResult(navController, file).apply(func)
}

class ChooseMemoryLinesFragmentResult(private val navController: NavController, private val file: File) {
    fun rightInformation() {
        R.id.title_memory.hasText("Mistborn")
        R.id.enter_memory_line.isDisplayed()
        R.id.last_time_update.isDisplayed()
    }

    fun goToNextScreen() {
        verify { navController.navigate(
            ChooseMemoryLineFragmentDirections.sendToBucket(
                file,
                MemoryLineBaseInformation(
                "idMemoryLine",
                "idOwner",
                "Mistborn"
                )
            )
        ) }
    }
}
