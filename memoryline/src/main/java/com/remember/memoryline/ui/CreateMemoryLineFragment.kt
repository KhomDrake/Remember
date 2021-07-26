package com.remember.memoryline.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.adapters.RecyclerViewAdapterSpinner
import com.remember.common.adapters.SpinnerItems
import com.remember.common.bottomsheet.SpinnerBottomSheetDialog
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.common.widget.RememberToolbar
import com.remember.common.widget.EventEditText
import com.remember.common.widget.ImageTextView
import com.remember.common.widget.EventTextView
import com.remember.common.widget.LoaderButton
import com.remember.extension.navControllerProvider
import com.remember.memoryline.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreateMemoryLineFragment: Fragment(R.layout.memory_line_create_memory_line_fragment) {

    private val navController: NavController by navControllerProvider()
    private val root: ViewGroup by viewProvider(R.id.root)
    private val toolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val memoryLineName: EventEditText by viewProvider(R.id.memory_line_name)
    private val chooseMemoryLineType: EventTextView by viewProvider(R.id.choose_memory_line_type)
    private val addParticipants: ImageTextView by viewProvider(R.id.add_participant)
    private val quantityParticipants: ImageTextView by viewProvider(R.id.quantity_participants)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_create_memory_line)
    private val createMemoryLineButton: LoaderButton by viewProvider(R.id.create_memory_line)
    private val createMemoryLineViewModel: CreateMemoryLineViewModel by sharedViewModel()
    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setOnClickListenerBackIcon {
            activity?.finish()
        }
        setupInputs()
        setupViewStateMachine()
    }

    override fun onStart() {
        super.onStart()
        createMemoryLineViewModel.type?.let {
            chooseMemoryLineType.setText(it.name)
        }
        val quantity = createMemoryLineViewModel.participantsChosen.value?.count() ?: 0
        quantityParticipants.setText(
            requireContext().resources.getQuantityString(
                R.plurals.memory_line_participants,
                quantity,
                quantity
            )
        )
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoading
            }

            state(stateLoading) {
                visibles(shimmer)
                gones(memoryLineName, chooseMemoryLineType, addParticipants, quantityParticipants, createMemoryLineButton)
            }

            state(stateData) {
                gones(shimmer)
                visibles(memoryLineName, chooseMemoryLineType, addParticipants, quantityParticipants, createMemoryLineButton)
            }
        }
    }

    private fun setupInputs() {
        createMemoryLineButton.setOnClickListener {
            if(memoryLineName.text.isEmpty()) {
                return@setOnClickListener
            }
            createMemoryLineViewModel.createMemoryLine(memoryLineName.text)
                .observe(viewLifecycleOwner) {
                    data {
                        showSuccess(R.string.memory_line_created_and_invites_send, root)
                        requireActivity().finish()
                    }
                    showLoading {
                        createMemoryLineButton.onLoading(true)
                    }
                    error { _ ->
                        createMemoryLineButton.onLoading(false)
                        showError(R.string.memory_line_failed_to_created, root)
                    }
                }
        }

        memoryLineName.whenShowInputBasedOnText { false }
        addParticipants.setOnClickListener {
            navController.navigate(R.id.action_createMemoryLineFragment_to_createMemoryLineAddParticipantFragment)
        }
        createMemoryLineViewModel.types().observe(viewLifecycleOwner) {
            data { types ->
                createMemoryLineViewModel.setNextPage(types.next)
                chooseMemoryLineType.onClickIcon {
                    SpinnerBottomSheetDialog()
                        .setTitleRes(R.string.memory_line_choose_memory_line_type_title)
                        .onClickItems {
                            createMemoryLineViewModel.setType(it)
                            chooseMemoryLineType.setText(it.name)
                        }
                        .onDismiss {
                            createMemoryLineViewModel.reset()
                            createMemoryLineViewModel.setNextPage(types.next)
                        }
                        .setItems(types.results.map { SpinnerItems(it.name, it.idType) })
                        .onErrorLoadMore { recyclerView ->
                            loadMore(recyclerView)
                        }
                        .onHasNextPage { createMemoryLineViewModel.hasNextPage }
                        .onLoadMore { recyclerView ->
                            loadMore(recyclerView)
                        }
                        .show(requireActivity().supportFragmentManager, CreateMemoryLineFragment::class.java.name)
                }
                viewStateMachine.changeState(stateData)
            }
            showLoading {
                viewStateMachine.changeState(stateLoading)
            }
        }
    }

    private fun loadMore(adapter: RecyclerViewAdapterSpinner) {
        createMemoryLineViewModel.types().observe(viewLifecycleOwner) {
            data {
                adapter.addData(it.results.map { spinnerItem -> SpinnerItems(spinnerItem.name, spinnerItem.idType) })
                createMemoryLineViewModel.setNextPage(it.next)
            }
            showLoading {
                adapter.addLoading()
            }
            error { _ ->
                adapter.addError()
            }
        }
    }

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        super.onDestroyView()
    }
}
