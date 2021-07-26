package com.remember.common.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.remember.common.R
import com.remember.repository.model.pagination.DataPagination
import com.remember.repository.model.pagination.ErrorPagination
import com.remember.repository.model.pagination.LoadingPagination
import com.remember.repository.model.pagination.NoMorePagination
import com.remember.repository.model.pagination.PaginationStatus

class PaginationDiffUtil: DiffUtil.ItemCallback<PaginationStatus>() {
    override fun areItemsTheSame(oldItem: PaginationStatus, newItem: PaginationStatus): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: PaginationStatus, newItem: PaginationStatus): Boolean {
        return oldItem == newItem
    }
}

const val VIEW_TYPE_DATA = 0
const val VIEW_TYPE_LOADING = 1
const val VIEW_TYPE_NO_MORE = 2
const val VIEW_TYPE_ERROR = 3

abstract class RecyclerViewAdapterPagination<T: DataPagination>(
    itemsInit: MutableList<PaginationStatus> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @get:LayoutRes
    abstract val layoutLoading: Int
    @get:LayoutRes
    abstract val layoutData: Int
    @get:StringRes
    abstract val noMoreMessage: Int
    @get:StringRes
    abstract val errorMessage: Int
    abstract val quantityToShowNoMore: Int

    val items = AsyncListDiffer(this, PaginationDiffUtil())

    val currentList: List<PaginationStatus>
        get() = items.currentList

    val canLoadMore: Boolean
        get() = currentList.contains(NoMorePagination).not()
                && currentList.contains(LoadingPagination).not()
                && currentList.contains(ErrorPagination).not()

    val hasNoMore: Boolean
        get() = currentList.contains(NoMorePagination)

    private var onErrorAction: () -> Unit = {}

    val itemsData: List<T>
        get() = currentList.filterIsInstance<DataPagination>().map { (it as T) }

    init {
        items.submitList(itemsInit)
    }

    abstract fun createData(view: View): DataViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(viewType) {
            VIEW_TYPE_DATA -> {
                val view = LayoutInflater.from(parent.context).inflate(layoutData, parent, false)
                createData(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(layoutLoading, parent, false)
                LoadingViewHolder(view)
            }
            VIEW_TYPE_ERROR -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.common_pagination_error, parent, false)
                ErrorViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.common_pagination_empty, parent, false)
                NoMoreViewHolder(view)
            }
        }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is DataPagination -> VIEW_TYPE_DATA
            is LoadingPagination -> VIEW_TYPE_LOADING
            is ErrorPagination -> VIEW_TYPE_ERROR
            else -> VIEW_TYPE_NO_MORE
        }
    }

    override fun getItemCount() = currentList.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is NoMoreViewHolder -> holder.bind(noMoreMessage)
            is ErrorViewHolder -> holder.bind(errorMessage, onErrorAction)
            is LoadingViewHolder -> Unit
            else -> (holder as DataViewHolder<T>).bind((currentList[position] as T))
        }
    }

    fun setOnError(onClick: () -> Unit) {
        onErrorAction = onClick
    }

    fun addLoading() {
        val current = currentList.toMutableList()

        if(current.contains(LoadingPagination)) current.remove(LoadingPagination)
        if(current.contains(ErrorPagination)) current.remove(ErrorPagination)
        if(current.contains(NoMorePagination)) current.remove(NoMorePagination)

        current.add(LoadingPagination)
        items.submitList(current)
    }

    fun addDataAll(data: List<T>) {
        items.submitList(data)
    }

    fun addData(data: List<T>) {
        val current = currentList.toMutableList()

        if(current.contains(ErrorPagination)) current.remove(ErrorPagination)
        if(current.contains(NoMorePagination)) current.remove(NoMorePagination)
        if(current.contains(LoadingPagination)) current.remove(LoadingPagination)

        current.addAll(data)
        items.submitList(current)
    }

    fun addError() {
        val current = currentList.toMutableList()

        if(current.contains(ErrorPagination)) current.remove(ErrorPagination)
        if(current.contains(LoadingPagination)) current.remove(LoadingPagination)
        if(current.contains(NoMorePagination)) current.remove(NoMorePagination)

        current.add(ErrorPagination)
        items.submitList(current)
    }

    fun addNoMore() {
        if(currentList.count() < quantityToShowNoMore) return

        val current = currentList.toMutableList()

        if(current.contains(NoMorePagination)) current.remove(NoMorePagination)
        if(current.contains(LoadingPagination)) current.remove(LoadingPagination)
        if(current.contains(ErrorPagination)) current.remove(ErrorPagination)

        current.add(NoMorePagination)
        items.submitList(current)
    }

    fun removeAll() {
        items.submitList(listOf())
    }
}

class LoadingViewHolder(val view: View) : RecyclerView.ViewHolder(view)

class NoMoreViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(@StringRes noMoreMessage: Int) {
        view.findViewById<AppCompatTextView>(R.id.pagination_empty_text).text = view.context.getString(noMoreMessage)
    }
}

class ErrorViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(@StringRes errorMessage: Int, onClick: () -> Unit) {
        val errorButton = view.findViewById<AppCompatButton>(R.id.pagination_error_button)
        errorButton.text = view.context.getString(errorMessage)
        errorButton.setOnClickListener {
            onClick.invoke()
        }
    }
}

abstract class DataViewHolder<V>(val view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(data: V)
}