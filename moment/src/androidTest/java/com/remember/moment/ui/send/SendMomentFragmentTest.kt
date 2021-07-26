package com.remember.moment.ui.send

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.IntentRule
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class SendMomentFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @get:Rule
    val intentRule = IntentRule()

    @Test
    fun whenSendMomentSuccess_shouldGoToHomeWithRightExtras() {
        sendMomentFragment {
            setupFileToBase64NotNull()
            sendMomentSuccess()
        } launch {

        } check {
            goToHome()
        }
    }

    @Test
    fun whenSendMomentFailure_shouldShowErrorScreen() {
        sendMomentFragment {
            setupFileToBase64NotNull()
            sendMomentFailure()
        } launch {

        } check {
            errorScreen()
        }
    }

    @Test
    fun whenShowErrorScreen_andClickTryAgain_shouldTryToSendMomentAgain() {
        sendMomentFragment {
            setupFileToBase64NotNull()
            sendMomentFailure()
        } launch {
            clickTryAgain()
        } check {
            sendMomentCallAgain()
        }
    }

    @Test
    fun whenToBase64ReturnNull_shouldShowErrorScreen() {
        sendMomentFragment {
            setupFileToBase64Null()
        } launch {

        } check {
            errorScreen()
        }
    }

    @Test
    fun whenSendMomentNotEnd_shouldShowUpload() {
        sendMomentFragment {
            setupFileToBase64NotNull()
            sendMomentLoading()
        } launch {

        } check {
            uploadState()
        }
    }

}