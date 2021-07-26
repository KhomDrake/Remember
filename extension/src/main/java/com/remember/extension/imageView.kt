package com.remember.extension

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

fun AppCompatImageView.centerCrop() : AppCompatImageView {
    this.scaleType = ImageView.ScaleType.CENTER_CROP
    return this
}

fun AppCompatImageView.center() : AppCompatImageView {
    this.scaleType = ImageView.ScaleType.CENTER
    return this
}

fun AppCompatImageView.centerFit() : AppCompatImageView {
    this.scaleType = ImageView.ScaleType.FIT_CENTER
    return this
}
