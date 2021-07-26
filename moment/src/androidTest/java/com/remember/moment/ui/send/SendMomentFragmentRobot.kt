package com.remember.moment.ui.send

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import com.remember.common.actions.MEMORY_LINE_ID
import com.remember.common.actions.MEMORY_LINE_NAME
import com.remember.common.actions.OWNER_ID
import com.remember.common.model.MemoryLineBaseInformation
import com.remember.extension.toBase64
import com.remember.moment.R
import com.remember.moment.ui.create.MomentViewModel
import com.remember.moment.ui.create.send.SendMomentFragment
import com.remember.moment.ui.create.send.SendMomentFragmentArgs
import com.remember.repository.model.moment.CreateMoment
import com.remember.repository.model.participant.AccountImage
import com.remember.test.extensions.*
import com.remember.test.utils.mockIntent
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.hamcrest.CoreMatchers.allOf
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import java.io.File
import java.lang.Exception

fun SendMomentFragmentTest.sendMomentFragment(func: SendMomentFragmentRobot.() -> Unit) =
    SendMomentFragmentRobot().apply(func)


class SendMomentFragmentRobot {

    private val viewModel: MomentViewModel = mockk(relaxed = true)
    private val file: File = mockk(relaxed = true)

    fun setupFileToBase64NotNull() {
        mockkStatic("com.remember.extension.FileKt")
        every { file.toBase64() } returns "sajdkhaskjdhsa"
    }

    fun setupFileToBase64Null() {
        mockkStatic("com.remember.extension.fileKt")
        every { file.toBase64() } throws Exception()
    }

    fun sendMomentSuccess() {
        every {
            viewModel.insertMemoryLine(any(), any(), any())
        } returns MutableResponseLiveData<CreateMoment>().apply {
            postData(
                CreateMoment(
                    "sadadsa",
                    "dsadasdsadas",
                    "adasdsadas",
                    AccountImage(
                        "asdsadsa",
                        "https://ddragon.leagueoflegends.com/cdn/img/champion/splash/Poppy_15.jpg"
                    ),
                    "adasdas",
                    "asdasdsa",
                    "asdasdsa"
                )
            )
        }
    }

    fun sendMomentFailure() {
        every {
            viewModel.insertMemoryLine(any(), any(), any())
        } returns MutableResponseLiveData<CreateMoment>().apply {
            postError(Throwable())
        }
    }

    fun sendMomentLoading() {
        every {
            viewModel.insertMemoryLine(any(), any(), any())
        } returns MutableResponseLiveData<CreateMoment>().apply {
            postLoading()
        }
    }

    infix fun launch(func: SendMomentFragmentRobot.() -> Unit) : SendMomentFragmentRobot {
        loadKoinModules(module(override = true) {
            viewModel { viewModel }
        })

        mockIntent("HOME")
        launchFragmentInContainer<SendMomentFragment>(
            SendMomentFragmentArgs(
                file,
                MemoryLineBaseInformation(
                "id",
                "test",
                "name"
                )
            ).toBundle(),
            themeResId = R.style.RememberAppTheme_Primary
        )

        return apply(func)
    }

    fun clickTryAgain() {
        R.id.error_button.click()
    }

    infix fun check(func: SendMomentFragmentResult.() -> Unit) =
        SendMomentFragmentResult(viewModel).apply(func)
}

class SendMomentFragmentResult(private val viewModel: MomentViewModel) {
    fun goToHome() {
        Intents.intended(allOf(
            "HOME".toMatcherIntent(),
            IntentMatchers.hasExtra(MEMORY_LINE_NAME, "name"),
            IntentMatchers.hasExtra(MEMORY_LINE_ID, "id"),
            IntentMatchers.hasExtra(OWNER_ID, "test")
        ))
    }

    fun errorScreen() {
        R.id.error_title.hasText(R.string.moment_send_moment_error_title.getText())
        R.id.error_text.hasText(R.string.moment_send_moment_error_body.getText())
        R.id.error_button.hasText(R.string.common_try_again.getText())
    }

    fun sendMomentCallAgain() {
        verify(exactly = 2) {
            viewModel.insertMemoryLine(any(), any(), any())
        }
    }

    fun uploadState() {
        R.id.upload_cloud_text.hasText(R.string.moment_send_moment_text.getText())
        R.id.upload_cloud_icon.isDisplayed()
        R.id.progress_bar.isDisplayed()
    }
}