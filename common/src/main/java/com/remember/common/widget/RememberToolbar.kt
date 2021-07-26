package com.remember.common.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R

class RememberToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val backIcon: AppCompatImageView by viewProvider(R.id.back_icon)
    private val title: AppCompatTextView by viewProvider(R.id.title)
    private val navigationIcon: AppCompatImageView by viewProvider(R.id.navigation_icon)
    private val avatarIcon: Avatar by viewProvider(R.id.avatar_icon)

    init {
        View.inflate(context, R.layout.common_widget_remember_toolbar, this)
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.RememberToolbar)
        setupText(attrs)
        setupBackIcon(attrs)
        setupNavigationIcon(attrs)
    }

    private fun setupNavigationIcon(attrs: TypedArray) {
        val hasNavigationIcon = attrs.hasValue(R.styleable.RememberToolbar_navigationIcon)
        navigationIcon.isVisible = hasNavigationIcon
        if(hasNavigationIcon) navigationIcon.setImageDrawable(attrs.getDrawable(R.styleable.RememberToolbar_navigationIcon))
    }

    fun setTitle(value: String?) {
        title.text = value
    }

    fun setAvatarVisibility(isVisible: Boolean) {
        avatarIcon.isVisible = isVisible
    }

    fun loadAvatar(url: String?) {
        avatarIcon.setImage(url)
    }

    private fun setupBackIcon(attrs: TypedArray) {
        val hasBackIcon = attrs.hasValue(R.styleable.RememberToolbar_backIcon)
        backIcon.isVisible = hasBackIcon
        if(hasBackIcon) backIcon.setImageDrawable(attrs.getDrawable(R.styleable.RememberToolbar_backIcon))
    }

    private fun setupText(attrs: TypedArray) {
        title.text = attrs.getText(R.styleable.RememberToolbar_android_text)
        val fontId = attrs.getResourceId(R.styleable.RememberToolbar_android_fontFamily, -1)
        title.typeface = ResourcesCompat.getFont(context, fontId)

        val defaultTextSize = resources.getDimension(R.dimen.remember_title_medium_size_text)
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrs.getDimension(R.styleable.RememberToolbar_android_textSize, defaultTextSize))
        title.setTextColor(attrs.getColor(R.styleable.RememberToolbar_android_textColor, -1))
    }

    fun setOnClickListenerBackIcon(l: () -> Unit) {
        backIcon.setOnClickListener {
            l.invoke()
        }
    }

    fun setOnClickListenerAvatarIcon(l: () -> Unit) {
        avatarIcon.setOnClickListener {
            l.invoke()
        }
    }

    fun setOnClickListenerNavigationIcon(l: () -> Unit) {
        navigationIcon.setOnClickListener {
            l.invoke()
        }
    }
}
