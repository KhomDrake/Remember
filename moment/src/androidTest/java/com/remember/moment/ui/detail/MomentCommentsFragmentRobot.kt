package com.remember.moment.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.moment.R
import com.remember.repository.MomentCommentsRepository
import com.remember.repository.model.comment.Comment
import com.remember.repository.model.comment.CommentsPagination
import com.remember.repository.model.memoryline.Owner
import com.remember.test.extensions.*
import com.remember.test.utils.mockResponseRepository
import com.remember.test.utils.mockToast
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.inject

fun MomentCommentsFragmentTest.momentCommentsFragment(func: MomentCommentsFragmentRobot.() -> Unit) =
    MomentCommentsFragmentRobot().apply(func)

class MomentCommentsFragmentRobot : KoinComponent {

    private val repository: MomentCommentsRepository by inject()

    fun insertCommentSuccess() {
        mockResponseRepository(repository::insertComment).postData(
            Comment(
                "",
                "",
                Owner(
                    "","","","", null
                ),
                "",
                "")
        )
    }

    fun insertCommentError() {
        mockResponseRepository(repository::insertComment).postError(
            Throwable()
        )
    }

    fun commentsSuccess() {
        mockResponseRepository(repository::commentsMoment).postData(
            CommentsPagination(
                null,
                null,
                10,
                listOf(
                    Comment(
                        idComment = "klsjdlaskd",
                        content = "Honor is dead",
                        owner = Owner(
                            id = "asdkjasl",
                            nickname = "Kaladin",
                            name = "hoid",
                            username = "cephandrus",
                            photo = null
                        ),
                        createdAt = "2020-06-15T22:17:47.303153-03:00",
                        updatedAt = "2020-06-15T22:17:47.303153-03:00"
                    )
                )
            )
        )
    }

    fun commentsError() {
        mockResponseRepository(repository::commentsMoment).postError(Throwable())
    }

    infix fun launch(func: MomentCommentsFragmentRobot.() -> Unit) : MomentCommentsFragmentRobot {
        mockToast()
        launchFragmentInContainer<MomentCommentsFragment>(
            themeResId = R.style.RememberAppTheme_Primary,
            fragmentArgs = MomentCommentsFragmentArgs("idMoment").toBundle()
        )
        return apply(func)
    }

    fun clickTryAgain() {
        R.id.error_button.click()
    }

    fun inputComment() {
        R.id.edit_text.typeText("We killed you")
    }

    fun clickSendIcon() {
        R.id.icon.click()
    }

    infix fun check(func: MomentCommentsFragmentResult.() -> Unit) =
        MomentCommentsFragmentResult().apply(func)
}

class MomentCommentsFragmentResult : KoinComponent {

    private val repository: MomentCommentsRepository by inject()

    fun comments() {
        R.id.comment_name.hasText("cephandrus")
        R.id.comment_content.hasText("Honor is dead")
        R.id.comment_date.hasText("Monday, 15 of june of 2020")
        R.id.comment_schedule.hasText("22:17")
    }

    fun errorScreen() {
        R.id.error_title.hasText(R.string.common_error_view_title_default.getText())
        R.id.error_text.hasText(R.string.moment_comments_error_body.getText())
        R.id.error_button.hasText(R.string.common_try_again.getText())
    }

    fun calledCommentsAgain() {
        verify(exactly = 2) {
            repository.commentsMoment(any(), any())
        }
    }

    fun sendIconDisplayed() {
        R.id.icon.isDisplayed()
    }

    fun calledInsertComment() {
        verify {
            repository.insertComment(any(), "We killed you")
        }
    }

    fun successMessage() {
        verify { any<Fragment>().showSuccess(
            R.string.moment_send_comment_success, any()
        ) }
    }

    fun errorMessage() {
        verify { any<Fragment>().showError(
            R.string.moment_send_comment_error, any()
        ) }
    }
}