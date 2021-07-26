package com.remember.test.extensions

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry

fun String.isTextDisplayed() = onView(withText(this)).check(matches(isDisplayed()))

fun String.isHintDisplayed() = onView(withHint(this)).check(matches(isDisplayed()))

fun String.textDoesNotExist() = onView(withText(this)).check(doesNotExist())

fun String.toMatcherIntent() = IntentMatchers.hasAction(
    "${InstrumentationRegistry.getInstrumentation().context.packageName}.$this"
)