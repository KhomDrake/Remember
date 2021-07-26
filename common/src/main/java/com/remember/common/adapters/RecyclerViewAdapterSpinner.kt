package com.remember.common.adapters

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.R
import com.remember.repository.model.pagination.DataPagination

class RecyclerViewAdapterSpinner(
    override val layoutLoading: Int = R.layout.common_spinner_item_placeholder,
    override val layoutData: Int = R.layout.common_spinner_item,
    override val noMoreMessage: Int = R.string.common_pagination_empty,
    override val errorMessage: Int = R.string.common_pagination_error,
    override val quantityToShowNoMore: Int = 6,
    spinnerItems: MutableList<SpinnerItems>,
    private val onClickItem: (SpinnerItems) -> Unit = {}
) : RecyclerViewAdapterPagination<SpinnerItems>(mutableListOf()) {

    init {
        addData(spinnerItems)
    }

    override fun createData(view: View): DataViewHolder<SpinnerItems> {
        return SpinnerViewHolder(view, onClickItem)
    }
}

class SpinnerItems(
    val name: String,
    val id: String
) : DataPagination(id)

class SpinnerViewHolder(
    view: View,
    private val onClickItem: (SpinnerItems) -> Unit
) : DataViewHolder<SpinnerItems>(view) {

    private val spinnerText: AppCompatTextView by viewProvider(R.id.spinner_item_text)

    override fun bind(data: SpinnerItems) {
        view.setOnClickListener {
            onClickItem.invoke(data)
        }
        spinnerText.text = data.name
    }
}
