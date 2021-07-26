package com.remember.common.extension

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.remember.common.R

private fun Fragment.setupTooltip(
    tooltipText: String,
    viewGroup: ViewGroup,
    @DrawableRes iconDrawable: Int,
    @DrawableRes backgroundDrawable: Int
) {
    val layout = LayoutInflater.from(requireContext()).inflate(R.layout.common_toast, viewGroup, false)
    val background = layout.findViewById<LinearLayoutCompat>(R.id.root)
    background.background = ContextCompat.getDrawable(requireContext(), backgroundDrawable)
    background.clipToOutline = true
    val text = layout.findViewById<AppCompatTextView>(R.id.text)
    text.text = tooltipText
    text.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(requireContext(), iconDrawable), null, null, null)
    Toast(requireContext().applicationContext).apply {
        view = layout
    }.show()
}

private fun Activity.setupTooltip(
    tooltipText: String,
    viewGroup: ViewGroup,
    @DrawableRes iconDrawable: Int,
    @DrawableRes backgroundDrawable: Int
) {
    val layout = LayoutInflater.from(this).inflate(R.layout.common_toast, viewGroup, false)
    val background = layout.findViewById<LinearLayoutCompat>(R.id.root)
    background.background = ContextCompat.getDrawable(this, backgroundDrawable)
    background.clipToOutline = true
    val text = layout.findViewById<AppCompatTextView>(R.id.text)
    text.text = tooltipText
    text.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, iconDrawable),null, null, null)
    Toast(applicationContext).apply {
        view = layout
    }.show()
}

fun Fragment.showError(@StringRes errorText: Int, viewGroup: ViewGroup) {
    setupTooltip(getString(errorText), viewGroup, R.drawable.common_ic_close, R.drawable.common_remember_background_red)
}

fun Fragment.showError(errorText: String, viewGroup: ViewGroup) {
    setupTooltip(errorText, viewGroup, R.drawable.common_ic_close, R.drawable.common_remember_background_red)
}

fun Activity.showError(@StringRes errorText: Int, viewGroup: ViewGroup) {
    setupTooltip(getString(errorText), viewGroup, R.drawable.common_ic_close, R.drawable.common_remember_background_red)
}

fun Activity.showError(errorText: String, viewGroup: ViewGroup) {
    setupTooltip(errorText, viewGroup, R.drawable.common_ic_close, R.drawable.common_remember_background_red)
}

fun Fragment.showSuccess(@StringRes successText: Int, viewGroup: ViewGroup) {
    setupTooltip(getString(successText), viewGroup, R.drawable.common_ic_check, R.drawable.common_remember_background_green)
}

fun Activity.showSuccess(@StringRes successText: Int, viewGroup: ViewGroup) {
    setupTooltip(getString(successText), viewGroup, R.drawable.common_ic_check, R.drawable.common_remember_background_green)
}

fun Activity.showSuccess(successText: String, viewGroup: ViewGroup) {
    setupTooltip(successText, viewGroup, R.drawable.common_ic_check, R.drawable.common_remember_background_green)
}
