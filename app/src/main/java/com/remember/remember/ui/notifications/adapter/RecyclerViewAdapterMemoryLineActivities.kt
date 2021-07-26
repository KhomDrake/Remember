package com.remember.remember.ui.notifications.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.remember.remember.R
import com.remember.repository.model.notification.MemoryLineActivity

class RecyclerViewAdapterMemoryLineActivities(
    private val memoryLineActivities: List<MemoryLineActivity>
) : RecyclerView.Adapter<ViewHolderMemoryLineActivities>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderMemoryLineActivities {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.app_memory_line_activies_item, parent, false)
        return ViewHolderMemoryLineActivities(view)
    }

    override fun getItemCount() = memoryLineActivities.count()

    override fun onBindViewHolder(holder: ViewHolderMemoryLineActivities, position: Int) = Unit
}

class ViewHolderMemoryLineActivities(private val view: View) : RecyclerView.ViewHolder(view)
