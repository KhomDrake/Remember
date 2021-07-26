package com.remember.common.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R

class CheckBoxTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val textView: AppCompatTextView by viewProvider(R.id.text_view)
    private val checkbox: AppCompatCheckBox by viewProvider(R.id.checkbox)
    lateinit var checkBoxValue: String
        private set

    val isChecked: Boolean
        get() = checkbox.isChecked

    init {
        inflate(context, R.layout.common_checkbox_text_view, this)
        setupAttributes(context, attrs)
        setOnClickListener {
            checkbox.isChecked = !checkbox.isChecked
        }
    }

    fun setValue(value: String) {
        checkBoxValue = value
    }

    fun setIsChecked(check: Boolean) {
        checkbox.isChecked = check
    }

    fun onCheckBoxClicked(func: (Boolean) -> Unit) {
        checkbox.setOnClickListener {
            func.invoke(isChecked)
        }
    }

    private fun setupAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.CheckBoxTextView)
        setupText(attrs)
    }

    private fun setupText(attrs: TypedArray) {
        textView.text = attrs.getText(R.styleable.CheckBoxTextView_android_text)
    }
}
