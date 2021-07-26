package com.remember.type.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.bottomsheet.CustomBottomSheetDialog
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.common.widget.EventEditText
import com.remember.common.widget.LoaderButton
import com.remember.common.widget.RememberToolbar
import com.remember.extension.onScroll
import com.remember.repository.model.MemoryLineType
import com.remember.type.R
import com.remember.type.ui.adapter.MemoryLineTypeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoryLineTypeActivity : AppCompatActivity(R.layout.type_activity_memory_line_type) {
    private val information: AppCompatImageView by viewProvider(R.id.information)
    private val root: ViewGroup by viewProvider(R.id.root)
    private val toolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val eventEditText: EventEditText by viewProvider(R.id.add_memory_line_type)
    private val types: RecyclerView by viewProvider(R.id.memory_line_types)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_memory_line_type)
    private val myTypes: AppCompatTextView by viewProvider(R.id.my_types_title)
    private val update: LoaderButton by viewProvider(R.id.update)
    private val memoryLineTypeViewModel: MemoryLineTypeViewModel by viewModel()
    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1
    private val itemTouchHelper = ItemTouchHelper(MoveItemTouchHelper())
    private val linearLayoutManager = LinearLayoutManager(
        this,
        LinearLayoutManager.VERTICAL,
        false
    )
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setOnClickListenerBackIcon {
            finish()
        }
        setupRecyclerView()
        setupEventEditText()
        setupViewStateMachine()
        setupUpdate()
        setupInformation()
        loadTypes(firstTime = true)
    }

    private fun setupInformation() {
        information.setOnClickListener {
            CustomBottomSheetDialog.Builder()
                .apply {
                    setTitle(R.string.type_bottom_sheet_information_title)
                    setMessage(R.string.type_bottom_sheet_information_message)
                    setPositiveButtonText(R.string.type_bottom_sheet_information_positive_button)
                    singlePositiveButton()
                    onClickPositiveButton { _, customBottomSheet ->
                        customBottomSheet.dismiss()
                    }
                }.show(supportFragmentManager)
        }
    }

    private fun setupRecyclerView() {
        itemTouchHelper.attachToRecyclerView(types)
        val adapter = MemoryLineTypeAdapter { typesAdapter ->
            updateVisibilityUpdateButton(typesAdapter)
        }
        adapter.touchHelper = itemTouchHelper
        adapter.setOnError {
            loadTypes()
        }
        types.layoutManager = linearLayoutManager
        types.adapter = adapter

        types.onScroll({lastVisibleItemPosition}) {
            when {
                typeAdapter.hasNoMore -> Unit
                memoryLineTypeViewModel.hasNextPage.not() -> {
                    typeAdapter.addNoMore()
                }
                typeAdapter.canLoadMore.not() -> Unit
                typeAdapter.canLoadMore -> loadTypes()
                else -> Unit
            }
        }
    }

    private fun updateVisibilityUpdateButton(types: List<MemoryLineType>) {
        update.enable = types != memoryLineTypeViewModel.types
    }

    private fun setupUpdate() {
        update.setOnClickListener {
            if(typeAdapter.itemsData == memoryLineTypeViewModel.types) {
                showError(R.string.type_move_items_to_update_error_message, root)
                return@setOnClickListener
            }

            memoryLineTypeViewModel.updateTypes(typeAdapter.itemsData).observe(this) {
                data {
                    showSuccess(R.string.type_update_types_success_message, root)
                    loadTypes(firstTime = true)
                    update.onLoading(false)
                }
                showLoading {
                    update.onLoading(true)
                }
                error { _ ->
                    update.onLoading(false)
                    showError(R.string.type_failed_to_update_error_message, root)
                }
            }
        }
    }

    private fun loadTypes(firstTime: Boolean = false) {
        if(firstTime) {
            memoryLineTypeViewModel.reset()
            typeAdapter.removeAll()
        }

        memoryLineTypeViewModel.loadTypes().observe(this@MemoryLineTypeActivity) {
            data {
                memoryLineTypeViewModel.setNextPage(it.next)
                typeAdapter.addData(it.results)
                updateVisibilityUpdateButton(typeAdapter.itemsData)
                if(firstTime) viewStateMachine.changeState(stateData)
            }
            showLoading {
                if(firstTime) {
                    viewStateMachine.changeState(stateLoading)
                } else {
                    typeAdapter.addLoading()
                }
            }
            error { _ ->
                typeAdapter.addError()
            }
        }
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoading
            }

            state(stateLoading) {
                visibles(shimmer)
                gones(types, eventEditText, myTypes, update, information)
            }

            state(stateData) {
                gones(shimmer)
                visibles(types, eventEditText, myTypes, update, information)
            }
        }
    }

    private fun setupEventEditText() {
        eventEditText.whenShowInputBasedOnText { it.length >= 2 }
        eventEditText.onEventClick {
            val priority = (types.adapter as? MemoryLineTypeAdapter)?.itemCount ?: 0
            memoryLineTypeViewModel.createType(
                eventEditText.text,
                 priority + 1
            ).observe(this) {
                data {
                    loadTypes(firstTime = true)
                }
                loading {
                    eventEditText.onLoading(it)
                }
                error { _ ->
                    showError(R.string.type_failed_to_create_type_error_message, root)
                }
            }
        }
    }

    private val typeAdapter: MemoryLineTypeAdapter
        get() = types.adapter as MemoryLineTypeAdapter

    override fun onDestroy() {
        viewStateMachine.shutdown()
        super.onDestroy()
    }
}
