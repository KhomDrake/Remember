package com.remember.extension

import android.content.Context
import android.content.Intent

fun Context.forAction(value: String) = Intent().setAction("$packageName.$value")
