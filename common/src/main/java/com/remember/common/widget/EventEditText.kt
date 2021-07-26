package com.remember.common.widget

import android.content.Context
import android.content.res.TypedArray
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R
import com.remember.extension.onTextChanged

typealias OnTextChanged = (String) -> Unit

class EventEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val editText: AppCompatEditText by viewProvider(R.id.edit_text)
    private val progressBar: ProgressBar by viewProvider(R.id.search_progress)
    private val icon: AppCompatImageView by viewProvider(R.id.icon)
    private val underline: View by viewProvider(R.id.underline)
    private var _lastValue: String = ""

    val lastValue: String
        get() = _lastValue

    val text: String
        get() = editText.text.toString()

    private var conditionToShow: (String) -> Boolean = { false }

    init {
        inflate(context, R.layout.common_event_edit_text_widget, this)
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.EventEditText)
        setupStyle(attrs)
    }

    private fun setupStyle(attrs: TypedArray) {
        underline.isVisible = attrs.getBoolean(R.styleable.EventEditText_hasUnderline, false)

        editText.hint = attrs.getText(R.styleable.EventEditText_android_hint)

        if(attrs.hasValue(R.styleable.EventEditText_android_indeterminateTint))
            progressBar.indeterminateTintList = attrs.getColorStateList(R.styleable.EventEditText_android_indeterminateTint)

        if(attrs.hasValue(R.styleable.EventEditText_android_textColor)) {
            underline.setBackgroundColor(attrs.getColor(R.styleable.EventEditText_android_textColor, -1))
            editText.setTextColor(attrs.getColor(R.styleable.EventEditText_android_textColor, -1))
        }

        if(attrs.hasValue(R.styleable.EventEditText_android_textColorHint))
            editText.setHintTextColor(attrs.getColor(R.styleable.EventEditText_android_textColorHint, -1))

        if(attrs.hasValue(R.styleable.EventEditText_android_background))
            background = attrs.getDrawable(R.styleable.EventEditText_android_background)

        if(attrs.hasValue(R.styleable.EventEditText_android_inputType))
            editText.inputType = attrs.getType(R.styleable.EventEditText_android_inputType)

        editText.maxLines = attrs.getInt(R.styleable.EventEditText_android_inputType, 1)

        icon.setImageDrawable(attrs.getDrawable(R.styleable.EventEditText_eventIcon))
    }

    fun setHint(value: String?) {
        editText.hint = value
    }

    fun setPaddingSearch() {
        val leftAndRight = context.resources.getDimension(R.dimen.remember_16dp).toInt()
        editText.setPadding(leftAndRight, editText.paddingTop, leftAndRight, editText.paddingBottom)
    }

    fun maxLength(max: Int) {
        editText.filters = arrayOf(InputFilter.LengthFilter(max))
    }

    fun saveValue() {
        _lastValue = editText.text.toString()
    }

    fun setText(text: String?) {
        editText.setText(text)
    }

    fun resetValue() {
        editText.setText("")
    }

    fun whenShowInputBasedOnText(condition: (String) -> Boolean) {
        conditionToShow = condition
        updateSearchVisibility()
        editText.onTextChanged {
            updateSearchVisibility()
        }
    }

    private fun updateSearchVisibility() {
        icon.isVisible = conditionToShow.invoke(text)
    }

    fun onTextChanged(onTextChanged: OnTextChanged) {
        editText.onTextChanged {
            onTextChanged.invoke(it)
        }
    }

    fun onLoading(loading: Boolean) {
        progressBar.isVisible = loading
        icon.isVisible = loading.not()
    }

    fun onEventClick(searchAction: () -> Unit) {
        icon.setOnClickListener {
            if(conditionToShow.invoke(text)) searchAction.invoke()
        }
    }
}
