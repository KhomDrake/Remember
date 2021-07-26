package com.remember.common.actions

import android.content.Context
import android.content.Intent
import com.remember.extension.forAction

fun Context.toHome() = forAction("HOME")
    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

fun Context.toLogo() = forAction("LOGO")
    .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

fun Context.toTerms() = forAction("TERMS")
    .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

fun Context.toHistory() = forAction("HISTORY")
