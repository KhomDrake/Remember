package com.remember.common.actions

import android.content.Context
import android.content.Intent
import com.remember.extension.forAction

fun Context.toOnboarding() = forAction("ONBOARDING")
    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

fun Context.toLogin() = forAction("LOGIN")
    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

fun Context.toIntro() = forAction("INTRO")
    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
