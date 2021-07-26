package com.remember.common.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R

class HintView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr)  {

    private val textHint: AppCompatTextView by viewProvider(R.id.hint_text)

    init {
        View.inflate(context, R.layout.common_hint_view, this)
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, set: AttributeSet?) {
        val attrs = context.obtainStyledAttributes(set, R.styleable.HintView)
        setupText(attrs)
    }

    private fun setupText(attrs: TypedArray) {
        textHint.text = attrs.getText(R.styleable.HintView_android_text)
    }
}
