package com.remember.onboarding.ui.intro

import android.content.Intent
import androidx.test.core.app.launchActivity
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.remember.common.actions.toLogin
import com.remember.common.actions.toOnboarding
import com.remember.onboarding.R
import com.remember.test.extensions.click
import com.remember.test.extensions.isTextDisplayed

fun IntroActivityTest.introActivity(func: IntroActivityRobot.() -> Unit) =
    IntroActivityRobot()

class IntroActivityRobot {

    infix fun launch(func: IntroActivityRobot.() -> Unit): IntroActivityRobot {
        Intents.intending(
            IntentMatchers.hasAction(
                InstrumentationRegistry.getInstrumentation().context.toLogin().action
            )
        )
        Intents.intending(
            IntentMatchers.hasAction(
                InstrumentationRegistry.getInstrumentation().context.toOnboarding().action
            )
        )
        launchActivity<IntroActivity>(Intent(
            InstrumentationRegistry.getInstrumentation().context,
            IntroActivity::class.java
        ))
        return apply(func)
    }

    fun clickCreateAccount() {
        R.id.create_account.click()
    }

    fun clickAlreadyHaveAccount() {
        R.id.already_have_account.click()
    }

    infix fun check(func: IntroActivityResult.() -> Unit) =
        IntroActivityResult().apply(func)

}

class IntroActivityResult {
    fun rightValues() {
        "Criar conta".isTextDisplayed()
        "Você tem guardado os melhores momentos da sua vida ?".isTextDisplayed()
        "Já possuo uma conta".isTextDisplayed()
    }

    fun goToOnboarding() {
        Intents.intended(
            IntentMatchers.hasAction(
                InstrumentationRegistry.getInstrumentation().context.toOnboarding().action
            )
        )
    }

    fun goToLogin() {
        Intents.intended(
            IntentMatchers.hasAction(
                InstrumentationRegistry.getInstrumentation().context.toLogin().action
            )
        )
    }

}