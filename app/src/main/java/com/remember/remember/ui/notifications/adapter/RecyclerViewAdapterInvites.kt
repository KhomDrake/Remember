package com.remember.remember.ui.notifications.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.remember.common.widget.Avatar
import com.remember.remember.R
import com.remember.repository.model.invite.Invite

typealias OnClickPositive = (Invite) -> Unit
typealias OnClickNegative = (Invite) -> Unit

class RecyclerViewAdapterInvites(
    private val invites: List<Invite>,
    private val onClickPositive: OnClickPositive,
    private val onClickNegative: OnClickNegative
) : RecyclerView.Adapter<ViewHolderInvites>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderInvites {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.app_invites_item, parent, false)
        return ViewHolderInvites(view)
    }

    override fun getItemCount() = invites.count()

    override fun onBindViewHolder(holder: ViewHolderInvites, position: Int) {
        holder.bind(invites[position], onClickNegative, onClickPositive)
    }
}

class ViewHolderInvites(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val inviteMessage: AppCompatTextView = itemView.findViewById(R.id.invite_text)
    private val positive: AppCompatButton = itemView.findViewById(R.id.accept)
    private val negative: AppCompatButton = itemView.findViewById(R.id.refuse)
    private val loading: ProgressBar = itemView.findViewById(R.id.loading)
    private val ownerAvatar: Avatar = itemView.findViewById(R.id.owner_profile_image)

    fun bind(invite: Invite, onClickNegative: OnClickNegative, onClickPositive: OnClickPositive) {
        onLoading(false)
        ownerAvatar.setImage(invite.owner.photo)
        inviteMessage.text = itemView.context?.getString(R.string.app_invite_messsage, invite.owner.username, invite.memoryLine.title)
        positive.setOnClickListener {
            onClickPositive.invoke(invite)
            onLoading()
        }
        negative.setOnClickListener {
            onClickNegative.invoke(invite)
            onLoading()
        }
    }

    private fun onLoading(on: Boolean = true) {
        positive.isGone = on
        negative.isGone = on
        inviteMessage.isGone = on
        ownerAvatar.isGone = on
        loading.isVisible = on
    }
}
