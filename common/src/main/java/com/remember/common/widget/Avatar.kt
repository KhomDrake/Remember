package com.remember.common.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import coil.load
import com.bumptech.glide.Glide
import com.remember.common.R
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class Avatar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val circleImage: CircleImageView
    @DrawableRes
    private var defaultDrawableRes: Int = R.drawable.common_ic_user_icon

    init {
        inflate(context, R.layout.common_avatar_widget, this)
        circleImage = findViewById(R.id.circle_image)
    }

    fun defaultPrimaryIcon() {
        defaultDrawableRes = R.drawable.common_ic_user_icon_primary
    }

    fun setImage(url: String?) {
        url?.let {
            Glide.with(this).load(url).placeholder(R.drawable.common_circle_shimmer).into(circleImage)
        } ?: defaultImage()
    }

    private fun defaultImage() {
        circleImage.setImageDrawable(ContextCompat.getDrawable(context, defaultDrawableRes))
    }
}

fun ImageView.loadDrawable(
    @DrawableRes
    drawable: Int,
    @DrawableRes placeholder: Int = R.drawable.common_shimmer_gradient,
    @DrawableRes error: Int = R.drawable.common_empty_bakcground,
    crossFadeDuration: Int = 2000
) {
    load(drawable) {
        placeholder(placeholder)
        crossfade(crossFadeDuration)
        error(error)
    }
}

fun ImageView.loadUrl(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.common_shimmer_gradient,
    @DrawableRes error: Int = R.drawable.common_empty_bakcground,
    crossFadeDuration: Int = 2000
) {
    load(url) {
        placeholder(placeholder)
        crossfade(crossFadeDuration)
        error(error)
    }
}

fun ImageView.loadUri(
    uri: String?,
    @DrawableRes placeholder: Int = R.drawable.common_shimmer_gradient,
    @DrawableRes error: Int = R.drawable.common_empty_bakcground,
    crossFadeDuration: Int = 2000
) {
    load(File(uri ?: "")) {
        placeholder(placeholder)
        crossfade(crossFadeDuration)
        error(error)
    }
}

fun Context.loadImageFromUrl(
    view: ImageView,
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.common_shimmer_gradient,
    @DrawableRes error: Int = R.drawable.common_empty_bakcground
) {
    Glide.with(this).load(url).placeholder(placeholder).error(error).fitCenter().into(view)
}
