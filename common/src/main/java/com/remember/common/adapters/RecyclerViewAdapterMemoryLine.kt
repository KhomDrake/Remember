package com.remember.common.adapters

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.remember.common.R
import com.remember.common.ui.memory_line.OnClickMemoryLine
import com.remember.common.ui.memory_line.OnClickMoreParticipants
import com.remember.common.widget.loadUrl
import com.remember.extension.toLocalDateTime
import com.remember.extension.differenceFromNow
import com.remember.repository.model.memoryline.MemoryLine
import de.hdodenhof.circleimageview.CircleImageView

class RecyclerViewAdapterMemoryLine(
    private val onClickMemoryLine: OnClickMemoryLine,
    private val onClickMoreParticipants: OnClickMoreParticipants,
    private val withImages: Boolean = true,
    override val layoutLoading: Int = R.layout.common_memory_line_shimmer_with_frame_layout,
    override val layoutData: Int = R.layout.common_memory_line_item,
    override val noMoreMessage: Int = R.string.common_pagination_empty,
    override val errorMessage: Int = R.string.common_pagination_error,
    override val quantityToShowNoMore: Int = 6
): RecyclerViewAdapterPagination<MemoryLine>(mutableListOf()) {

    fun addMemoryLine(memoryLines: List<MemoryLine>) {
        addData(memoryLines)
    }

    override fun createData(view: View): DataViewHolder<MemoryLine> {
        return ViewHolderMemoryLine(view, onClickMemoryLine, onClickMoreParticipants, withImages)
    }
}

class ViewHolderMemoryLine(
    itemView: View,
    private val onClickMemoryLine: OnClickMemoryLine,
    private val onClickMoreParticipants: OnClickMoreParticipants,
    private val withImages: Boolean
) : DataViewHolder<MemoryLine>(itemView) {

    private var root: ConstraintLayout = itemView.findViewById(R.id.root)
    private var firstMoment: AppCompatImageView = itemView.findViewById(R.id.first_moment)
    private var secondMoment: AppCompatImageView = itemView.findViewById(R.id.second_moment)
    private var enterMemoryLine: CircleImageView = itemView.findViewById(R.id.enter_memory_line)
    private var hasMoreParticipants: CircleImageView = itemView.findViewById(R.id.has_more_participants)
    private var titleMemory: AppCompatTextView = itemView.findViewById(R.id.title_memory)
    private var lastTimeUpdate: AppCompatTextView = itemView.findViewById(R.id.last_time_update)

    override fun bind(data: MemoryLine) {

        titleMemory.text = data.title
        lastTimeUpdate.text = data.updatedDate.toLocalDateTime().differenceFromNow(firstMoment.context)
        root.clipToOutline = true
        firstMoment.isVisible = withImages
        secondMoment.isVisible = withImages

        if(withImages) {
            firstMoment.scaleType = ImageView.ScaleType.CENTER_CROP
            secondMoment.scaleType = ImageView.ScaleType.CENTER_CROP

            val uriFirstMoment = data.moments.getOrNull(0)?.file
            val uriSecondMoment = data.moments.getOrNull(1)?.file

            data.moments.count()

            firstMoment.loadUrl(uriFirstMoment)
            secondMoment.loadUrl(uriSecondMoment)

            firstMoment.setOnClickListener {
                onClickMemoryLine.invoke(data)
            }

            secondMoment.setOnClickListener {
                onClickMemoryLine.invoke(data)
            }
        }

        enterMemoryLine.setOnClickListener {
            onClickMemoryLine.invoke(data)
        }

        hasMoreParticipants.setOnClickListener {
            onClickMoreParticipants.invoke(data.id)
        }

        hasMoreParticipants.isVisible = data.participants.count() > 0
    }
}
