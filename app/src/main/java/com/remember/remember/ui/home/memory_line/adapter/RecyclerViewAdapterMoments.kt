package com.remember.remember.ui.home.memory_line.adapter

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.adapters.DataViewHolder
import com.remember.common.adapters.RecyclerViewAdapterPagination
import com.remember.common.widget.Avatar
import com.remember.common.widget.loadUrl
import com.remember.extension.centerCrop
import com.remember.extension.toFormattedString
import com.remember.extension.toLocalDate
import com.remember.extension.visible
import com.remember.extension.invisible
import com.remember.remember.R
import com.remember.repository.model.moment.Moment

typealias OnClickMoment = (String, String, View) -> Unit
typealias OnClickComments = (String, View) -> Unit

class RecyclerViewAdapterMoments(
    private val onClickMoment: OnClickMoment,
    private val onClickComments: OnClickComments,
    private val commentsVisible: Boolean = true,
    override val layoutLoading: Int = R.layout.app_moment_shimmer_with_frame_layout,
    override val layoutData: Int = R.layout.app_moment,
    override val noMoreMessage: Int = R.string.common_pagination_empty,
    override val errorMessage: Int = R.string.common_pagination_error,
    override val quantityToShowNoMore: Int = 6
) : RecyclerViewAdapterPagination<Moment>(mutableListOf()) {

    fun addMoments(list: List<Moment>) {
        addData(list)
    }

    override fun createData(view: View) = ViewHolderMoments(view, onClickMoment, onClickComments, commentsVisible)
}

class ViewHolderMoments(
    private val myView: View,
    private val onClickMoment: OnClickMoment,
    private val onClickComments: OnClickComments,
    private val commentsVisible: Boolean = true
) : DataViewHolder<Moment>(myView) {

    private val image: AppCompatImageView by viewProvider(R.id.image_moment)
    private val owner: Avatar by viewProvider(R.id.owner)
    private val description: AppCompatTextView by viewProvider(R.id.description)
    private val date: AppCompatTextView by viewProvider(R.id.date)
    private val comments: AppCompatTextView by viewProvider(R.id.comments)

    override fun bind(data: Moment) {
        image.clipToOutline = true
        comments.setOnClickListener { onClickComments.invoke(data.idMoment, image) }
        image.setOnClickListener { onClickMoment.invoke(data.idMoment, data.urlBucket, image) }

        if(commentsVisible)
            comments.visible()
        else
            comments.invisible()

        image.centerCrop()
        image.loadUrl(data.urlBucket)

        description.text = data.description

        owner.setImage(data.owner.photo)
        date.text = data.creationDate.toLocalDate().toFormattedString()
    }
}
