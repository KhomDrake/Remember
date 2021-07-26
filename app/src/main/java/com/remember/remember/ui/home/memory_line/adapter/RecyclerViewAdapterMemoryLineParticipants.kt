package com.remember.remember.ui.home.memory_line.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.remember.common.widget.Avatar
import com.remember.common.adapters.OnClickParticipant
import com.remember.remember.R
import com.remember.repository.model.participant.ParticipantSearch
import com.remember.repository.model.participant.toUserSearch

class RecyclerViewAdapterMemoryLineParticipants(
    private val participants: List<ParticipantSearch>,
    private val onClickParticipant: OnClickParticipant = {}
) : RecyclerView.Adapter<ViewHolderParticipants>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderParticipants {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.app_participant_item, parent, false)
        return ViewHolderParticipants(view)
    }

    override fun getItemCount() = participants.count()

    override fun onBindViewHolder(holder: ViewHolderParticipants, position: Int) {
        holder.bind(participants[position], onClickParticipant)
    }
}

class ViewHolderParticipants(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val profile: Avatar = itemView.findViewById(R.id.participant_profile_image)
    private val nameParticipant: AppCompatTextView = itemView.findViewById(R.id.participant_name)
    private val owner: AppCompatTextView = itemView.findViewById(R.id.owner_memory_line)

    fun bind(participant: ParticipantSearch, onClickParticipant: OnClickParticipant) {
        itemView.setOnClickListener { onClickParticipant.invoke(participant.toUserSearch()) }
        profile.setImage(participant.photo)
        nameParticipant.text = participant.nickname
        owner.isVisible = participant.isOwner
    }
}
