package com.remember.moment.ui.detail

import com.remember.common.initializer.KoinModules
import com.remember.repository.module.mockedRepositories
import com.remember.test.rule.KoinRule
import org.junit.Rule
import org.junit.Test

class MomentCommentsFragmentTest {

    @get:Rule
    val koinRule = KoinRule(KoinModules.modules + mockedRepositories)

    @Test
    fun whenCommentsSuccess_shouldShowComments() {
        momentCommentsFragment {
            commentsSuccess()
        } launch {

        } check {
            comments()
        }
    }

    @Test
    fun whenCommentsError_shouldShowError() {
        momentCommentsFragment {
            commentsError()
        } launch {

        } check {
            errorScreen()
        }
    }

    @Test
    fun whenError_andClickTryAgain_shouldCallCommentsAgain() {
        momentCommentsFragment {
            commentsError()
        } launch {
            clickTryAgain()
        } check {
            calledCommentsAgain()
        }
    }

    @Test
    fun whenInputComment_shouldDisplayedSendIcon() {
        momentCommentsFragment {
            commentsSuccess()
        } launch {
            inputComment()
        } check {
            sendIconDisplayed()
        }
    }

    @Test
    fun whenInputComment_andClickSendIcon_shouldCallInsertComment() {
        momentCommentsFragment {
            commentsSuccess()
            insertCommentSuccess()
        } launch {
            inputComment()
            clickSendIcon()
        } check {
            calledInsertComment()
        }
    }

    @Test
    fun whenCallInsertCommentSuccess_shouldShowSuccessMessageAndCommentsCalledAgain() {
        momentCommentsFragment {
            commentsSuccess()
            insertCommentSuccess()
        } launch {
            inputComment()
            clickSendIcon()
        } check {
            successMessage()
        }
    }

    @Test
    fun whenCallInsertCommentError_shouldShowErrorMessage() {
        momentCommentsFragment {
            commentsSuccess()
            insertCommentError()
        } launch {
            inputComment()
            clickSendIcon()
        } check {
            errorMessage()
        }
    }
}