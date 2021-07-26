package com.remember.remember.ui.terms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.remember.R
import com.remember.repository.model.terms.TermItem
import com.skydoves.expandablelayout.ExpandableLayout

class TermsAdapterRecyclerView(
    private val termItems: List<TermItem>
) : RecyclerView.Adapter<ViewHolder>() {

    private val normalItem = 0
    private val expandableItem = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            expandableItem -> ExpandableViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.app_term_item_expandable, parent, false)
            )
            else -> NormalViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.app_term_item, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (termItems[position].type.toLowerCase()) {
            TermsItemType.EXPANDABLE.name.toLowerCase() -> expandableItem
            else -> normalItem
        }
    }

    override fun getItemCount() = termItems.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(termItems[position])
    }
}

enum class TermsItemType {
    EXPANDABLE
}

abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(termItem: TermItem)
}

class ExpandableViewHolder(view: View): ViewHolder(view) {

    private val expandableLayout: ExpandableLayout by viewProvider(R.id.expandable_layout)
    private val expandableTitle: AppCompatTextView by viewProvider(R.id.title_expandable_parent)

    override fun bind(termItem: TermItem) {
        expandableTitle.text = termItem.title
        expandableLayout.setOnClickListener {
            if(expandableLayout.isExpanded) expandableLayout.collapse() else expandableLayout.expand()
        }
        expandableLayout.secondLayout.findViewById<LinearLayoutCompat>(R.id.content)?.run {
            termItem.items.forEach { item ->
                val view = LayoutInflater.from(itemView.context).inflate(R.layout.app_term_item, this, false)
                val termTitle = view.findViewById<AppCompatTextView>(R.id.term_title)
                termTitle.text = item.title
                val termSubTitle = view.findViewById<AppCompatTextView>(R.id.term_sub_title)
                termSubTitle.text = item.subTitle
                val termDescription = view.findViewById<AppCompatTextView>(R.id.term_description)
                termDescription.text = item.description

                termTitle.isVisible = item.title != null
                termSubTitle.isVisible = item.subTitle != null
                termDescription.isVisible = item.description != null

                addView(view)
            }
            requestLayout()
        }
    }
}

class NormalViewHolder(view: View): ViewHolder(view) {

    private val termTitle: AppCompatTextView by viewProvider(R.id.term_title)
    private val termSubTitle: AppCompatTextView by viewProvider(R.id.term_sub_title)
    private val termDescription: AppCompatTextView by viewProvider(R.id.term_description)

    override fun bind(termItem: TermItem) {
        termTitle.isVisible = termItem.title != null
        termSubTitle.isVisible = termItem.subTitle != null
        termDescription.isVisible = termItem.description != null

        termTitle.text = termItem.title
        termSubTitle.text = termItem.subTitle
        termDescription.text = termItem.description
    }
}
