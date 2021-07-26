package com.remember.common.widget

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.remember.common.R
import br.com.arch.toolkit.delegate.viewProvider

class LoaderButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val loaderButton: AppCompatTextView by viewProvider(R.id.button_loader)
    private val loading: ProgressBar by viewProvider(R.id.progress_bar)

    private var _enable = true

    var enable: Boolean
        get() = _enable
        set(value) {
            _enable = value
            updateEnable()
        }

    val text: String
        get() = loaderButton.text.toString()

    init {
        View.inflate(context, R.layout.common_loader_button_widget, this)
        setupAttributes(context, attrs)
    }

    fun setText(value: String?) {
        loaderButton.text = value
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        loaderButton.setOnClickListener(l)
    }

    fun onLoading(on: Boolean) {
        loading.isVisible = on
        loaderButton.isInvisible = on
    }

    fun show(button: Boolean = false, load: Boolean = false) {
        loaderButton.isVisible = button
        loading.isVisible = load
    }

    private fun updateEnable() {
        if (enable) {
            alpha = 1f
            loaderButton.isEnabled = true
        } else {
            alpha = .4f
            loaderButton.isEnabled = false
        }
    }

    private fun setupAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.LoaderButton)
        setupText(attrs, context)
        setupLoading(attrs)
        enable = attrs.getBoolean(R.styleable.LoaderButton_enabled, true)
        updateEnable()
    }

    private fun setupLoading(attrs: TypedArray) {
        loading.background = attrs.getDrawable(R.styleable.LoaderButton_android_background)
        loading.indeterminateTintList = ColorStateList.valueOf(attrs.getColor(R.styleable.LoaderButton_android_textColor, -1))
    }

    private fun setupText(attrs: TypedArray, context: Context) {
        loaderButton.text = attrs.getText(R.styleable.LoaderButton_android_text)

        val fontId = attrs.getResourceId(R.styleable.LoaderButton_android_fontFamily, -1)
        loaderButton.typeface = ResourcesCompat.getFont(context, fontId)

        val defaultTextSize = resources.getDimension(R.dimen.remember_small_medium_text)
        loaderButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, attrs.getDimension(R.styleable.LoaderButton_android_textSize, defaultTextSize))
        loaderButton.setTextColor(attrs.getColor(R.styleable.LoaderButton_android_textColor, -1))

        if(attrs.getBoolean(R.styleable.LoaderButton_caps, true).not()) {
            loaderButton.transformationMethod = null
        }
        background = attrs.getDrawable(R.styleable.LoaderButton_android_background)
    }
}
