package com.remember.moment.ui.select_memory_line

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class ChooseMemoryLineFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun whenTypesSuccess_shouldShowRightInformation() {
        chooseMemoryLineFragment {
            typesSuccess()
        } launch {

        } check {
            rightInformation()
        }
    }

    @Test
    fun whenTypesFailure_shouldShowErrorScreen() {
        chooseMemoryLineFragment {
            typesFailure()
        } launch {

        } check {
            errorScreen()
        }
    }

    @Test
    fun whenShowErrorScreen_andClickTryAgain_shouldMakeRequestTypeAgain() {
        chooseMemoryLineFragment {
            typesFailure()
        } launch {
            clickTryAgain()
        } check {
            typeRequestedAgain()
        }
    }

    @Test
    fun whenTypesLoading_shouldShowLoadingScreen() {
        chooseMemoryLineFragment {
            typesLoading()
        } launch {

        } check {
            loadingScreen()
        }
    }

    @Test
    @Ignore("Fix me please")
    fun whenClickBack_shouldCallNavigateUp() {
        chooseMemoryLineFragment {
            typesSuccess()
        } launch {
            clickBack()
        } check {
            navigateUp()
        }
    }

}