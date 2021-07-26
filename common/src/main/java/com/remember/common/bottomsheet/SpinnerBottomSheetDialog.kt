package com.remember.common.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R
import com.remember.common.adapters.RecyclerViewAdapterSpinner
import com.remember.common.adapters.SpinnerItems
import com.remember.extension.onScroll

class SpinnerBottomSheetDialog : RoundedBottomSheetDialogFragment(R.layout.common_spinner_bottom_sheet_dialog, canDismiss = false) {

    private val items: RecyclerView by viewProvider(R.id.spinner_items)
    private val title: AppCompatTextView by viewProvider(R.id.spinner_title)
    private var onClickSpinner: (SpinnerItems) -> Unit = {}
    private var onLoadMore: (RecyclerViewAdapterSpinner) -> Unit = {}
    private var onHasNextPage: () -> Boolean = { false }
    private var onError: (RecyclerViewAdapterSpinner) -> Unit = {}
    private var titleTextRes: Int = 0
    private var onDismiss: () -> Unit = {}
    private var titleText: String = ""
    private var spinnerItems = mutableListOf<SpinnerItems>()
    private val linearLayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
    )
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    fun setTitle(value: String) : SpinnerBottomSheetDialog {
        titleText = value
        return this
    }

    fun setTitleRes(@StringRes stringRes: Int) : SpinnerBottomSheetDialog {
        titleTextRes = stringRes
        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = if(titleTextRes != 0) {
            requireContext().getString(titleTextRes)
        } else {
            titleText
        }

        items.layoutManager = linearLayoutManager
        val adapter = RecyclerViewAdapterSpinner(spinnerItems = spinnerItems) {
            onClickSpinner.invoke(it)
            dismiss()
        }
        items.adapter = adapter

        adapter.setOnError {
            onError.invoke(adapter)
        }

        items.onScroll({lastVisibleItemPosition}) {
            when {
                adapter.hasNoMore -> Unit
                onHasNextPage.invoke().not() -> Unit
                adapter.canLoadMore.not() -> Unit
                adapter.canLoadMore -> {
                    onLoadMore.invoke(adapter)
                }
                else -> Unit
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismiss.invoke()
    }

    fun onDismiss(dismiss: () -> Unit) : SpinnerBottomSheetDialog {
        onDismiss = dismiss
        return this
    }

    fun onErrorLoadMore(onErrorLoadMore: (RecyclerViewAdapterSpinner) -> Unit) : SpinnerBottomSheetDialog {
        onError = onErrorLoadMore
        return this
    }

    fun onHasNextPage(hasNextPage: () -> Boolean) : SpinnerBottomSheetDialog {
        onHasNextPage = hasNextPage
        return this
    }

    fun onLoadMore(onLoadMore: (RecyclerViewAdapterSpinner) -> Unit) : SpinnerBottomSheetDialog {
        this.onLoadMore = onLoadMore
        return this
    }

    fun setItems(spinnerItems: List<SpinnerItems>) : SpinnerBottomSheetDialog {
        this.spinnerItems = spinnerItems.toMutableList()
        return this
    }

    fun onClickItems(onClickSpinner: (SpinnerItems) -> Unit) : SpinnerBottomSheetDialog {
        this.onClickSpinner = onClickSpinner
        return this
    }
}
