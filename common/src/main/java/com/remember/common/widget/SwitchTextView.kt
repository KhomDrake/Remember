package com.remember.common.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SwitchCompat
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R

class SwitchTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val switchCompat: SwitchCompat by viewProvider(R.id.switch_compat_widget)
    private val text: AppCompatTextView by viewProvider(R.id.text_view_widget)

    var isChecked: Boolean
        get() = switchCompat.isChecked
        set(value) {
            switchCompat.isChecked = value
        }

    init {
        orientation = HORIZONTAL
        View.inflate(context, R.layout.common_switch_text_view_widget, this)
        setupAttr(context.obtainStyledAttributes(attrs, R.styleable.SwitchTextView))
    }

    private fun setupAttr(attrs: TypedArray) {
        if (attrs.hasValue(R.styleable.SwitchTextView_android_text))
            text.text = attrs.getText(R.styleable.SwitchTextView_android_text)

        switchCompat.isEnabled = attrs.getBoolean(R.styleable.SwitchTextView_isEnabled, true)
        switchCompat.isChecked = attrs.getBoolean(R.styleable.SwitchTextView_isChecked, false)
    }

    fun onSwitchChanged(onChange: (isChecked: Boolean) -> Unit) {
        switchCompat.setOnCheckedChangeListener { _, isChecked ->
            onChange.invoke(isChecked)
        }
    }
}
