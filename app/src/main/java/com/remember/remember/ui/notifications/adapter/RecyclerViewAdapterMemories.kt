package com.remember.remember.ui.notifications.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.remember.remember.R
import com.remember.repository.model.notification.Memories

class RecyclerViewAdapterMemories(
    private val memories: List<Memories>
) : RecyclerView.Adapter<ViewHolderMemories>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMemories {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.app_memories_item, parent, false)
        return ViewHolderMemories(view)
    }

    override fun getItemCount() = memories.count()

    override fun onBindViewHolder(holder: ViewHolderMemories, position: Int) = Unit
}

class ViewHolderMemories(private val view: View) : RecyclerView.ViewHolder(view)
