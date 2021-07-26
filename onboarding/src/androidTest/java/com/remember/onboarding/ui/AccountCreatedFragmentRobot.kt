package com.remember.onboarding.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.remember.common.actions.toHistory
import com.remember.common.actions.toLogin
import com.remember.onboarding.R
import com.remember.onboarding.ui.register.AccountCreatedFragment
import com.remember.test.extensions.click
import com.remember.test.extensions.isTextDisplayed

fun AccountCreatedFragmentTest.accountCreatedFragment(func: AccountCreatedFragmentRobot.() -> Unit) =
    AccountCreatedFragmentRobot().apply(func)

class AccountCreatedFragmentRobot {

    fun mockGoToRememberHistory() {
        Intents.intending(IntentMatchers.hasAction(
            InstrumentationRegistry.getInstrumentation().context.toHistory().action
        ))
    }

    fun mockGoToFirstAccess() {
        Intents.intending(IntentMatchers.hasAction(
            InstrumentationRegistry.getInstrumentation().context.toLogin().action
        ))
    }

    infix fun launch(func: AccountCreatedFragmentRobot.() -> Unit): AccountCreatedFragmentRobot {
        launchFragmentInContainer<AccountCreatedFragment>(null, R.style.RememberAppTheme_Primary)
        return apply(func)
    }

    infix fun check(func: AccountCreatedFragmentResult.() -> Unit) =
        AccountCreatedFragmentResult().apply(func)

    fun clickRememberHistory() {
        R.id.remember_history.click()
    }

    fun clickFirstAccess() {
        R.id.first_access.click()
    }
}

class AccountCreatedFragmentResult {
    fun rightTexts() {
        "Seu usuário foi criado.".isTextDisplayed()
        "Antes de fazer o primeiro acesso, deseja conhecer a história do Remember".isTextDisplayed()
        "HISTÓRIA DO REMEMBER".isTextDisplayed()
        "Primeiro Acesso".isTextDisplayed()
    }

    fun goToRememberHistory() {
        Intents.intending(IntentMatchers.hasAction(
            InstrumentationRegistry.getInstrumentation().context.toHistory().action
        ))
    }

    fun goToFirstAccess() {
        Intents.intending(IntentMatchers.hasAction(
            InstrumentationRegistry.getInstrumentation().context.toLogin().action
        ))
    }

}