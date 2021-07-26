package com.remember.common.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R

class ImageTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val image: AppCompatImageView by viewProvider(R.id.image)
    private val text: AppCompatTextView by viewProvider(R.id.text)

    init {
        inflate(context, R.layout.common_image_text_view, this)
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.ImageTextView)
        setupText(attrs, context)
    }

    fun setText(newValue: String) {
        text.text = newValue
    }

    private fun setupText(attrs: TypedArray, context: Context) {
        text.text = attrs.getText(R.styleable.ImageTextView_android_text)

        val fontId = attrs.getResourceId(R.styleable.ImageTextView_android_fontFamily, -1)
        text.typeface = ResourcesCompat.getFont(context, fontId)

        val defaultTextSize = resources.getDimension(R.dimen.remember_small_medium_text)
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrs.getDimension(R.styleable.ImageTextView_android_textSize, defaultTextSize))
        text.setTextColor(attrs.getColor(R.styleable.ImageTextView_android_textColor, -1))

        image.setImageDrawable(attrs.getDrawable(R.styleable.ImageTextView_image))
    }
}
