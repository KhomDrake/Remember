package com.remember.extension

import android.app.Activity
import android.view.View
import androidx.annotation.ColorRes

fun Activity.setStatusBar(@ColorRes id: Int) {
    window.statusBarColor = getColor(id)
}

fun Activity.setStatusBarIconsLight() {
    window.decorView.systemUiVisibility = 0
}

fun Activity.setStatusBarIconsDark() {
     window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}
