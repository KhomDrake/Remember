package com.remember.moment.ui.select_memory_line

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class ChooseMemoryLinesFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun whenMemoryLineSuccess_shouldShowRightInformation() {
        chooseMemoryLinesFragment {
            memoryLineSuccess()
        } launch {

        } check {
            rightInformation()
        }
    }

    @Test
    fun whenMemoryLineSuccess_andSelectMemoryLine_shouldGoToNextScreen() {
        chooseMemoryLinesFragment {
            memoryLineSuccess()
        } launch {
            clickFirstMemoryLine()
        } check {
            goToNextScreen()
        }
    }
}