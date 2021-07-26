package com.remember.common.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R

class EventTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val eventText: AppCompatTextView by viewProvider(R.id.event_text)
    private val eventIcon: AppCompatImageView by viewProvider(R.id.icon)
    private val underline: View by viewProvider(R.id.underline)

    val text: String
        get() = eventText.text.toString()

    init {
        inflate(context, R.layout.common_event_text_view_widget, this)
        setupAttributes(context, attrs)
    }

    fun setText(newValue: String) {
        eventText.text = newValue
    }

    private fun setupAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.EventTextView)
        setupStyle(attrs)
    }

    fun onClickIcon(func: () -> Unit) {
        eventIcon.setOnClickListener {
            func.invoke()
        }
    }

    private fun setupStyle(attrs: TypedArray) {
        underline.isVisible = attrs.getBoolean(R.styleable.EventTextView_hasUnderlineTextView, false)

        eventText.hint = attrs.getText(R.styleable.EventTextView_android_hint)

        if(attrs.hasValue(R.styleable.EventTextView_android_background))
            background = attrs.getDrawable(R.styleable.EventTextView_android_background)

        eventIcon.setImageDrawable(attrs.getDrawable(R.styleable.EventTextView_eventIconTextView))
    }
}
