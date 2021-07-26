package com.remember.test.utils

import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import io.mockk.every
import io.mockk.mockkStatic
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun mockIntent(action: String, targetContext: Boolean = false) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    Intents.intending(
        IntentMatchers.hasAction(
            "${context.packageName}.$action"
        )
    ).respondWith(Instrumentation.ActivityResult(0, null))
}

fun mockIntent(action: String, targetContext: Boolean = false, vararg matchers: Matcher<Intent>) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    Intents.intending(
        Matchers.allOf(
            matchers.toMutableList().apply {
                add(IntentMatchers.hasAction(
                    "${context.packageName}.$action"
                ))
            }
        )
    ).respondWith(Instrumentation.ActivityResult(0, null))
}

fun checkIntent(action: String, targetContext: Boolean = false, vararg matchers: Matcher<Intent>) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    Intents.intended(
        Matchers.allOf(
            matchers.toMutableList().apply {
                add(IntentMatchers.hasAction(
                    "${context.packageName}.$action"
                ))
            }
        )
    )
}

fun checkIntent(action: String, targetContext: Boolean = false) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext else
        InstrumentationRegistry.getInstrumentation().context
    Intents.intended(
        IntentMatchers.hasAction(
            "${context.packageName}.$action"
        )
    )
}

fun mockNavigationActivity() {
    mockkStatic("androidx.navigation.Navigation")
}

fun mockAuthentication() {
    mockkStatic("com.remember.network.auth.AuthenticationKt")
}

fun mockNavigationFragment() {
    mockkStatic("androidx.navigation.fragment.NavHostFragment")
}

fun mockBuildConfig() {
    mockkStatic("com.remember.remember.BuildConfig")
}

fun mockToast() {
    mockkStatic("com.remember.common.extension.ToastKt")
}

inline fun <reified T>mockResponseRepository(
    crossinline request: () -> ResponseLiveData<T>
) : MutableResponseLiveData<T> {
    val liveData = MutableResponseLiveData<T>()
    every { request.invoke() } returns liveData
    return liveData
}

inline fun <reified T, reified First: Any>mockResponseRepository(
    crossinline request: (First) -> ResponseLiveData<T>
) : MutableResponseLiveData<T> {
    val liveData = MutableResponseLiveData<T>()
    every { request.invoke(any()) } returns liveData
    return liveData
}

inline fun <reified T, reified First: Any, reified Second: Any>mockResponseRepository(
    crossinline request: (First, Second) -> ResponseLiveData<T>
) : MutableResponseLiveData<T> {
    val liveData = MutableResponseLiveData<T>()
    every { request.invoke(any(), any()) } returns liveData
    return liveData
}

inline fun <reified T, reified First: Any, reified Second: Any, reified Third: Any>mockResponseRepository(
    crossinline request: (First, Second, Third) -> ResponseLiveData<T>
)  : MutableResponseLiveData<T> {
    val liveData = MutableResponseLiveData<T>()
    every { request.invoke(any(), any(), any()) } returns liveData
    return liveData
}
