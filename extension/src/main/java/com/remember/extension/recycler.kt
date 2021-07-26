package com.remember.extension

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.onScroll(inLastItem: () -> Int,  scroll: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            recyclerView.layoutManager?.let {
                if(inLastItem.invoke() + 1 == it.itemCount) scroll.invoke()
            }
        }
    })
}
