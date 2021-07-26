package com.remember.remember.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.widget.ErrorView
import com.remember.common.widget.RememberToolbar
import com.remember.extension.navControllerProvider
import com.remember.extension.setStatusBar
import com.remember.extension.setStatusBarIconsLight
import com.remember.remember.R
import com.remember.remember.ui.home.memory_line.adapter.RecyclerViewAdapterMoments
import com.remember.repository.model.moment.Moment
import de.hdodenhof.circleimageview.CircleImageView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoryLineHistoryFragment : Fragment(R.layout.app_fragment_memory_line_history) {

    private val navController: NavController by navControllerProvider()
    private val historyViewModel: HistoryViewModel by viewModel()

    private val rememberToolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val hasMoreMembers: CircleImageView by viewProvider(R.id.has_more_participants)
    private val moments: RecyclerView by viewProvider(R.id.moments)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer)
    private val errorView: ErrorView by viewProvider(R.id.retrievable_error)

    private val viewStateMachine = ViewStateMachine()
    private val stateLoadingMoments = 0
    private val stateDataMoments = 1
    private val stateErrorMoments = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBar(R.color.statusBarColor)
        setStatusBarIconsLight()
        setupToolbar()
        setupRetrievableError()
        setupViewStateMachine()
        setupRecyclerView()
        setupHasMoreMembers()
        loadMoments()
    }

    private fun setupHasMoreMembers() {
        hasMoreMembers.setOnClickListener {
            navController.navigate(
                MemoryLineHistoryFragmentDirections.actionMemoryLineHistoryFragmentToParticipantsHistoryFragment()
            )
        }
    }

    private fun setupRecyclerView() {
        moments.setupAdapter(listOf())
    }

    private fun RecyclerView.setupAdapter(moments: List<Moment>
    ) {
        adapter = RecyclerViewAdapterMoments(
            commentsVisible = false,
            onClickComments = { _, _ ->

            },
            onClickMoment = { _, url, _ ->
                navController.navigate(
                    MemoryLineHistoryFragmentDirections.actionMemoryLineHistoryFragmentToMomentHistoryFragment(
                        url
                    )
                )
            }
        )
        momentsAdapter.addMoments(moments)
        layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupToolbar() {
        rememberToolbar.setOnClickListenerBackIcon {
            requireActivity().finish()
        }
    }

    private fun setupRetrievableError() {
        errorView.setOnClickListener {
            loadMoments()
        }
    }

    private fun loadMoments() {
        historyViewModel.memoryLineHistory()
            .observe(viewLifecycleOwner) {
                data {
                    moments.momentsAdapter.addMoments(it)
                    viewStateMachine.changeState(stateDataMoments)
                }
                showLoading(withData = false) {
                    viewStateMachine.changeState(stateLoadingMoments)
                }
                error { _ ->
                    viewStateMachine.changeState(stateErrorMoments)
                }
            }
    }

    private val RecyclerView.momentsAdapter: RecyclerViewAdapterMoments
        get() = adapter as RecyclerViewAdapterMoments

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoadingMoments
            }

            state(stateLoadingMoments) {
                visibles(shimmer)
                gones(moments, errorView)
            }

            state(stateDataMoments) {
                visibles(moments)
                gones(shimmer, errorView)
            }

            state(stateErrorMoments) {
                visibles(errorView)
                gones(moments, shimmer)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }
}
