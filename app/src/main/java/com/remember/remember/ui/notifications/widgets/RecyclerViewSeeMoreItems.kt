package com.remember.remember.ui.notifications.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.remember.R

class RecyclerViewSeeMoreItems @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private val title: AppCompatTextView by viewProvider(R.id.recycler_view_see_more_title)
    private val items: RecyclerView by viewProvider(R.id.recycler_view_see_more_items)
    private val seeMoreText: AppCompatTextView by viewProvider(R.id.recycler_view_see_more_text)
    private val emptyText: AppCompatTextView by viewProvider(R.id.recycler_view_see_more_empty_text)
    private var quantityItemsToShowSeeMore: Int = 3

    init {
        orientation = VERTICAL
        inflate(context, R.layout.app_recyclerview_see_more_items, this)
        items.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setupAttrs(context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewSeeMoreItems))
        seeMoreText.paintFlags = seeMoreText.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    private fun setupAttrs(attrs: TypedArray) {
        if (attrs.hasValue(R.styleable.RecyclerViewSeeMoreItems_emptyText))
            emptyText.text = attrs.getText(R.styleable.RecyclerViewSeeMoreItems_emptyText)

        if (attrs.hasValue(R.styleable.RecyclerViewSeeMoreItems_titleText))
            title.text = attrs.getText(R.styleable.RecyclerViewSeeMoreItems_titleText)

        if (attrs.hasValue(R.styleable.RecyclerViewSeeMoreItems_seeMoreText))
            seeMoreText.text = attrs.getText(R.styleable.RecyclerViewSeeMoreItems_seeMoreText)
    }

    fun setSeeMoreText(text: String) {
        seeMoreText.text = text
    }

    fun setSeeMoreText(@StringRes text: Int) {
        seeMoreText.text = context.getText(text)
    }

    fun setTitle(text: String) {
        title.text = text
    }

    fun setTitle(@StringRes text: Int) {
        title.text = context.getText(text)
    }

    fun setEmptyText(text: String) {
        emptyText.text = text
    }

    fun setEmptyText(@StringRes text: Int) {
        emptyText.text = context.getText(text)
    }

    fun setupRecycler(adapter: RecyclerView.Adapter<*>, quantityItems: Int) {
        items.adapter = adapter
        seeMoreText.isVisible = quantityItems > quantityItemsToShowSeeMore
        items.isVisible = quantityItems > 0
        emptyText.isVisible = quantityItems == 0
    }
}