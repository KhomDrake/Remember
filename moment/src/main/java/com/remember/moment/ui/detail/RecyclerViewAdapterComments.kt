package com.remember.moment.ui.detail

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.remember.common.adapters.DataViewHolder
import com.remember.common.adapters.RecyclerViewAdapterPagination
import com.remember.common.widget.Avatar
import com.remember.extension.PATTERN_HOUR_AND_MINUTE
import com.remember.extension.toFormattedString
import com.remember.extension.toLocalDate
import com.remember.extension.toLocalDateTime
import com.remember.moment.R
import com.remember.repository.model.comment.Comment

class RecyclerViewAdapterComments(
    override val layoutLoading: Int = R.layout.moment_comment_shimmer_frame_layout,
    override val layoutData: Int = R.layout.moment_comment_item,
    override val noMoreMessage: Int = R.string.common_pagination_empty,
    override val errorMessage: Int = R.string.common_message_error,
    override val quantityToShowNoMore: Int = 10
) : RecyclerViewAdapterPagination<Comment>() {
    override fun createData(view: View) = ViewHolder(view)
}

class ViewHolder(view: View) : DataViewHolder<Comment>(view) {
    override fun bind(data: Comment) {
        val picture: Avatar = view.findViewById(R.id.owner_comment_picture)
        val name: AppCompatTextView = view.findViewById(R.id.comment_name)
        val date: AppCompatTextView = view.findViewById(R.id.comment_date)
        val content: AppCompatTextView = view.findViewById(R.id.comment_content)
        val commentSchedule: AppCompatTextView = view.findViewById(R.id.comment_schedule)

        picture.defaultPrimaryIcon()
        picture.setImage(data.owner.photo)
        name.text = data.owner.username
        date.text = data.createdAt.toLocalDate().toFormattedString()
        commentSchedule.text = data.createdAt.toLocalDateTime().toFormattedString(PATTERN_HOUR_AND_MINUTE)
        content.text = data.content
    }
}
