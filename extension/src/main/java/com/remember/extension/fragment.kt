package com.remember.extension

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.requestPermission(permissions: Array<String>, code: Int) {
    activity?.let {
        requestPermissions(
            permissions,
            code
        )
    }
}

fun Activity.requestPermission(permissions: Array<String>, code: Int) {
    ActivityCompat.requestPermissions(
        this,
        permissions,
        code
    )
}

fun Context.checkPermissions(permissions: Array<String>) : Boolean {
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(
                this, permission) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

fun Fragment.checkPermissions(permissions: Array<String>) : Boolean {
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(
                requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

fun Fragment.setStatusBar(@ColorRes id: Int) {
    activity?.let {
        it.window.statusBarColor = it.getColor(id)
    }
}

fun Fragment.setStatusBarIconsLight() {
    activity?.let {
        it.window.decorView.systemUiVisibility = 0
    }
}

fun Fragment.setStatusBarIconsDark() {
    activity?.let {
        it.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
