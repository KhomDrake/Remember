package com.remember.test.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.remember.test.actions.ClickIgnoreConstraint
import com.remember.test.actions.ClickOnChildView
import com.remember.test.matchers.RecyclerViewItemViewMatcher
import com.remember.test.matchers.RecyclerViewMatcherQuantityItems
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

fun Int.typeText(text: String): ViewInteraction = onView(withId(this))
    .perform(ViewActions.typeText(text), closeSoftKeyboard())

fun Int.click(ignoreConstraint: Boolean = false): ViewInteraction =
    onView(withId(this)).perform(if(ignoreConstraint) ClickIgnoreConstraint() else ViewActions.click())

fun Int.isTextDisplayed(targetContext: Boolean = false) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    val text = context.getString(this)
    text.isTextDisplayed()
}

fun Int.hintDisplayed(targetContext: Boolean = false) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    val text = context.getString(this)
    text.isHintDisplayed()
}

fun Int.getText(targetContext: Boolean = false) : String {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    return context.getString(this)
}

fun Int.textNotDisplayed() {
    val text = InstrumentationRegistry.getInstrumentation().context.getString(this)
    text.textDoesNotExist()
}

fun Int.isDisplayed() {
    onView(withId(this)).check(matches(ViewMatchers.isDisplayed()))
}

fun Int.isNotDisplayed() {
    onView(withId(this)).check(matches(not(ViewMatchers.isDisplayed())))
}

fun Int.hasHint(text: String) {
    onView(withId(this)).check(matches(ViewMatchers.withHint(text)))
}

fun Int.hasText(text: String) {
    onView(withId(this)).check(matches(ViewMatchers.withText(text)))
}

fun Int.isEnable() {
    onView(withId(this)).check(matches(ViewMatchers.isEnabled()))
}

fun Int.isDisable() {
    onView(withId(this)).check(matches(not(ViewMatchers.isEnabled())))
}

fun Int.checkRecyclerViewQuantityOfItems(quantityOfItems: Int) {
    onView(withId(this)).check(matches(RecyclerViewMatcherQuantityItems(quantityOfItems)))
}

fun Int.checkViewOnRecyclerViewPosition(position: Int, childId: Int, viewMatcher: Matcher<View>) {
    onView(RecyclerViewItemViewMatcher(this, position, childId))
        .check(matches(viewMatcher))
}

fun Int.clickOnRecyclerViewInsideItem(position: Int, childId: Int) =
    onView(withId(this)).perform(
        actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position, ClickOnChildView(childId)
        )
    )

fun Int.clickOnRecyclerViewItem(position: Int) =
    onView(withId(this)).perform(
        actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position, ViewActions.click()
        )
    )
