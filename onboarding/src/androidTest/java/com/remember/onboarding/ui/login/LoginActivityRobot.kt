package com.remember.onboarding.ui.login

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.DefaultTaskExecutor
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import com.remember.common.actions.toHome
import com.remember.common.extension.showError
import com.remember.onboarding.R
import com.remember.repository.AuthRepository
import com.remember.repository.model.auth.AuthorizationData
import com.remember.repository.model.auth.Signin
import com.remember.test.extensions.click
import com.remember.test.extensions.isTextDisplayed
import com.remember.test.extensions.typeText
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.verify
import org.koin.core.KoinComponent
import org.koin.core.inject

fun LoginActivityTest.loginActivity(func: LoginActivityRobot.() -> Unit) =
    LoginActivityRobot().apply(func)

private lateinit var activity: Activity

class LoginActivityRobot : KoinComponent {

    private val authRepository: AuthRepository by inject()

    fun userWithAuthorizationData() {
        every { authRepository.setup() } returns MutableResponseLiveData<Boolean>().apply {
            postData(true)
        }
    }

    fun userWithoutAuthorizationData() {
        every { authRepository.setup() } returns MutableResponseLiveData<Boolean>().apply {
            postError(Throwable())
        }
    }

    fun loginFailed() {
        every { authRepository.login(Signin(
            any(), any()
        )) } returns MutableResponseLiveData<Boolean>().apply {
            postError(Throwable())
        }
    }

    fun loginSuccess() {
        every { authRepository.login(
            Signin(
                any(), any()
        )
        ) } returns MutableResponseLiveData<Boolean>().apply {
            postData(true)
        }
    }

    fun notAcceptedTerms() {
        every { authRepository.login(
            Signin(
                any(), any()
            )
        ) } returns MutableResponseLiveData<Boolean>().apply {
            postData(false)
        }
    }

    fun acceptedTerms() {
        every { authRepository.login(
            Signin(
                any(), any()
            )
        ) } returns MutableResponseLiveData<Boolean>().apply {
            postData(true)
        }
    }

    infix fun launch(func: LoginActivityRobot.() -> Unit) : LoginActivityRobot {
        mockkStatic("com.remember.common.extension.ToastKt")

        Intents.intending(IntentMatchers.hasAction(
            "${InstrumentationRegistry.getInstrumentation().context.packageName}.LOGO"
        )).respondWith(Instrumentation.ActivityResult(0, null))

        Intents.intending(IntentMatchers.hasAction(
            "${InstrumentationRegistry.getInstrumentation().context.packageName}.TERMS"
        )).respondWith(Instrumentation.ActivityResult(0, null))

        ActivityScenario.launch<LoginActivity>(Intent(
            InstrumentationRegistry.getInstrumentation().context,
            LoginActivity::class.java
        )).onActivity { ac ->
            activity = ac
        }
        return this.apply(func)
    }

    fun inputRightUsername() {
        R.id.input_username_or_email.typeText("username")
    }

    fun inputRightPassword() {
        R.id.input_password.typeText("password")
    }

    fun clickLoginButton() {
        R.id.login_button.click()
    }

    fun inputWrongUsername() {
        R.id.input_username_or_email.typeText("username1")
    }

    fun inputWrongPassword() {
        R.id.input_password.typeText("password1")
    }

    infix fun check(func: LoginActivityResult.() -> Unit) =
        LoginActivityResult().apply(func)
}

class LoginActivityResult {
    fun goToHome() {
        Intents.intended(IntentMatchers.hasAction(
            "${InstrumentationRegistry.getInstrumentation().context.packageName}.LOGO"
        ))
    }

    fun toTerms() {
        Intents.intended(IntentMatchers.hasAction(
            "${InstrumentationRegistry.getInstrumentation().context.packageName}.TERMS"
        ))
    }

    fun notGoToHome() {
        Intents.intended(IntentMatchers.anyIntent())
        Intents.times(1)
    }

    fun inputUsernameTitle() {
        "E-mail / Usuário".isTextDisplayed()
    }

    fun inputPasswordTitle() {
        "Senha".isTextDisplayed()
    }

    fun loginButton() {
        "Acessar".isTextDisplayed()
    }

    fun errorMessage() {
        verify {
            activity.showError(R.string.onboarding_login_failed, activity.findViewById(R.id.root))
        }
    }

    fun loginSuccess() {
        Intents.intended(IntentMatchers.hasAction(
            Intent.ACTION_MAIN
        ))
    }
}