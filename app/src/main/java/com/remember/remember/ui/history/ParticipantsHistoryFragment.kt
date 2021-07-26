package com.remember.remember.ui.history

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
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
import com.remember.remember.R
import com.remember.remember.ui.home.memory_line.adapter.RecyclerViewAdapterMemoryLineParticipants
import com.remember.repository.model.participant.ParticipantFull
import com.remember.repository.model.participant.ParticipantSearch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParticipantsHistoryFragment : Fragment(R.layout.app_fragment_memory_line_participants) {

    private val historyViewModel: HistoryViewModel by viewModel()
    private val navController: NavController by navControllerProvider()
    private val participants: RecyclerView by viewProvider(R.id.users)
    private val noParticipants: AppCompatTextView by viewProvider(R.id.no_participant)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_participants)
    private val rememberToolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val errorView: ErrorView by viewProvider(R.id.error_view)
    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1
    private val stateEmpty = 2
    private val stateError = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewStateMachine()
        setStatusBar(R.color.statusBarColor)
        rememberToolbar.setOnClickListenerBackIcon {
            navController.navigateUp()
        }
        errorView.setOnClickListener {
            loadHistory()
        }
    }

    override fun onStart() {
        super.onStart()
        loadHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }

    private fun loadHistory() {
        historyViewModel.participantsHistory()
            .observe(viewLifecycleOwner) {
                data {
                    setupParticipants(it)
                    viewStateMachine.changeState(if (it.count() == 0) stateEmpty else stateData)
                }
                showLoading {
                    viewStateMachine.changeState(stateLoading)
                }
                error { _ ->
                    viewStateMachine.changeState(stateError)
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
                gones(participants, noParticipants, errorView)
            }

            state(stateData) {
                visibles(participants)
                gones(shimmer, noParticipants, errorView)
            }

            state(stateEmpty) {
                visibles(noParticipants)
                gones(shimmer, participants, errorView)
            }

            state(stateError) {
                visibles(errorView)
                gones(shimmer, participants, noParticipants)
            }
        }
    }

    private fun setupParticipants(participants: List<ParticipantFull>) {
        this.participants.adapter =
            RecyclerViewAdapterMemoryLineParticipants(
                participants.map { ParticipantSearch(it) }
            )
        this.participants.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
