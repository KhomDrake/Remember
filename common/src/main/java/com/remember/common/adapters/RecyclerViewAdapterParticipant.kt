package com.remember.common.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.remember.common.R
import com.remember.common.widget.Avatar
import com.remember.repository.model.search.UserSearch

typealias OnClickParticipant = (UserSearch) -> Unit

class RecyclerViewAdapterParticipant(
    private val participants: List<UserSearch>,
    private val onClickParticipant: OnClickParticipant = {}
) : RecyclerView.Adapter<ViewHolderParticipant>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderParticipant {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.common_participant_memory_line_item, parent, false)
        return ViewHolderParticipant(view)
    }

    override fun getItemCount() = participants.count()

    override fun onBindViewHolder(holder: ViewHolderParticipant, position: Int) {
        holder.bind(participants[position], onClickParticipant)
    }
}

class ViewHolderParticipant(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: AppCompatTextView = itemView.findViewById(R.id.name_participant)
    private val profile: Avatar = itemView.findViewById(R.id.image_profile)

    fun bind(participant: UserSearch, onClickParticipant: OnClickParticipant) {
        itemView.setOnClickListener {
            onClickParticipant.invoke(participant)
        }
        profile.setImage(participant.picture)
        name.text = participant.username
    }
}
