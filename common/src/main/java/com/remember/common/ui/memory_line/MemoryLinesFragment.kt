package com.remember.common.ui.memory_line

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.arch.toolkit.delegate.viewProvider
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.R
import com.remember.common.adapters.RecyclerViewAdapterMemoryLine
import com.remember.common.widget.ErrorView
import com.remember.extension.onScroll
import com.remember.repository.model.memoryline.MemoryLine
import org.koin.androidx.viewmodel.ext.android.viewModel

typealias OnClickMemoryLine = (MemoryLine) -> Unit
typealias OnClickMoreParticipants = (String) -> Unit

private const val TYPE_MEMORY_LINE = "Type"
private const val TITLE_TYPE_MEMORY_LINE = "Name"
private const val WITH_IMAGES = "withImages"

abstract class MemoryLinesFragment(
    private var typeMemoryLine: String = "",
    private var titleText: String = "",
    private var withImages: Boolean = true
) : Fragment(R.layout.common_fragment_memory_lines) {

    private val title: AppCompatTextView by viewProvider(R.id.title)
    private val memoryLines: RecyclerView by viewProvider(R.id.memory_line_list)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer)
    private val errorView: ErrorView by viewProvider(R.id.retrievable_error)
    private val swipeRefreshLayout: SwipeRefreshLayout by viewProvider(R.id.swipe_refresh)
    private val memoryLineViewModel: MemoryLineViewModel by viewModel()
    private val linearLayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
    )
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TYPE_MEMORY_LINE, typeMemoryLine)
        outState.putString(TITLE_TYPE_MEMORY_LINE, titleText)
        outState.putBoolean(WITH_IMAGES, withImages)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            typeMemoryLine = it.getString(TYPE_MEMORY_LINE, typeMemoryLine)
            titleText = it.getString(TITLE_TYPE_MEMORY_LINE, titleText)
            withImages = it.getBoolean(WITH_IMAGES, true)
        }
        title.text = titleText
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupScroll()
        setupSwipeRefresh()
        setupRecyclerView()
        setupErrorView()
    }

    private fun setupErrorView() {
        errorView.setOnClickListener {
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {
        memoryLineViewModel.reset()
        val onClickMemoryLine = { memoryLine: MemoryLine ->
            onClickMemoryLine(memoryLine)
        }

        val onClickMoreParticipants = { id: String ->
            onClickMoreParticipants(id)
        }

        memoryLines.adapter = RecyclerViewAdapterMemoryLine(
            onClickMemoryLine,
            onClickMoreParticipants,
            withImages
        )
        memoryLines.layoutManager = linearLayoutManager
        memoryLines.memoryLineAdapter.setOnError {
            loadMemoryLine(isFirst = false)
        }
        loadMemoryLine(isFirst = true)
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            setupRecyclerView()
        }
    }

    abstract fun onClickMemoryLine(memoryLine: MemoryLine)

    abstract fun onClickMoreParticipants(id: String)

    private fun loadMemoryLine(isFirst: Boolean) {
        memoryLineViewModel.memoryLineByType(typeMemoryLine)
            .observe(viewLifecycleOwner) {
                data {
                    if(isFirst) {
                        swipeRefreshLayout.isRefreshing = false
                        show(content = true)
                    }

                    memoryLines.memoryLineAdapter.addMemoryLine(it.results)
                    memoryLineViewModel.setNextPage(it.next)
                }
                showLoading {
                    if(isFirst) show(loading = true)
                    else memoryLines.memoryLineAdapter.addLoading()
                }
                error { _ ->
                    if(isFirst) show(error = true)
                    else memoryLines.memoryLineAdapter.addError()
                }
            }
    }

    private fun setupScroll() {
        memoryLines.onScroll({ lastVisibleItemPosition }) {
            val memoryLineAdapter = memoryLines.memoryLineAdapter
            when {
                memoryLineAdapter.hasNoMore -> Unit
                memoryLineViewModel.hasNextPage.not() -> {
                    memoryLineAdapter.addNoMore()
                }
                memoryLineAdapter.canLoadMore.not() -> Unit
                else -> loadMemoryLine(isFirst = false)
            }
        }
    }

    private fun show(content: Boolean = false, loading: Boolean = false, error: Boolean = false) {
        swipeRefreshLayout.isVisible = content
        shimmer.isVisible = loading
        errorView.isVisible = error
    }

    private val RecyclerView.memoryLineAdapter: RecyclerViewAdapterMemoryLine
        get() = adapter as RecyclerViewAdapterMemoryLine
}
