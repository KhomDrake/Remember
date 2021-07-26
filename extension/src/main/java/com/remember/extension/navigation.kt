package com.remember.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlin.reflect.KProperty

fun AppCompatActivity.navControllerProvider(@IdRes idRes: Int) =
    NavControllerProvider(idRes)

fun Fragment.navControllerProvider() = NavControllerProvider(0)

class NavControllerProvider(@IdRes private val idRes: Int) {

    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>) =
        thisRef.findNavController(idRes)

    operator fun getValue(thisRef: Fragment, property: KProperty<*>) =
        thisRef.findNavController()
}
