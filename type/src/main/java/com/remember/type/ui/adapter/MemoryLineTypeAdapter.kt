package com.remember.type.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.remember.common.adapters.DataViewHolder
import com.remember.common.adapters.RecyclerViewAdapterPagination
import com.remember.repository.model.MemoryLineType
import com.remember.type.R

class MemoryLineTypeAdapter(
    private val onItemMove: (List<MemoryLineType>) -> Unit
) : RecyclerViewAdapterPagination<MemoryLineType>(mutableListOf()) {

    lateinit var touchHelper: ItemTouchHelper

    fun onItemMove(from: Int, to: Int) {
        val current = currentList.toMutableList()
        val item = current[from]
        current.removeAt(from)
        current.add(to, item)
        items.submitList(current)
        onItemMove.invoke(itemsData)
    }

    override val layoutLoading: Int
        get() = R.layout.type_memory_line_type_layout_placeholder
    override val layoutData: Int
        get() = R.layout.type_memory_line_type_layout
    override val noMoreMessage: Int
        get() = R.string.type_no_more_types
    override val errorMessage: Int
        get() = R.string.common_message_error
    override val quantityToShowNoMore: Int
        get() = 6

    override fun createData(view: View): DataViewHolder<MemoryLineType> {
        return VH(view) {
            touchHelper.startDrag(it)
        }
    }
}

class VH(
    view: View,
    private val onLongPress: (RecyclerView.ViewHolder) -> Unit
): DataViewHolder<MemoryLineType>(view), GestureDetector.OnGestureListener, View.OnTouchListener {

    lateinit var gestureDetector: GestureDetector

    override fun bind(data: MemoryLineType) {
        val name = view.findViewById<AppCompatTextView>(R.id.name_memory_line_type)
        name.text = data.name
        gestureDetector = GestureDetector(itemView.context, this)
    }

    override fun onShowPress(e: MotionEvent?) = Unit

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        onLongPress.invoke(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }
}
