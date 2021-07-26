package com.remember.common.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R
import com.remember.common.widget.Avatar
import com.remember.repository.model.search.UserSearch

class RecyclerViewAdapterChosenParticipants(
    private val participants: MutableList<UserSearch>,
    private val onClickParticipant: OnClickParticipant = {}
) : RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.common_choose_participant_memory_line_item, parent, false)
        return VH(view)
    }

    override fun getItemCount() = participants.count()

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(participants[position], onClickParticipant)
    }
}

class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val profileImage: Avatar by viewProvider(R.id.profile_image_participant)
    private val removeParticipant: AppCompatImageView by viewProvider(R.id.remove_participant)

    fun bind(participant: UserSearch, onClickParticipant: OnClickParticipant) {
        profileImage.setImage(participant.picture)
        removeParticipant.setOnClickListener {
            onClickParticipant.invoke(participant)
        }
    }
}
