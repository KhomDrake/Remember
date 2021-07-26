package com.remember.remember.ui.notifications

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.common.widget.ErrorView
import com.remember.extension.setStatusBar
import com.remember.remember.R
import com.remember.remember.ui.notifications.adapter.OnClickNegative
import com.remember.remember.ui.notifications.adapter.OnClickPositive
import com.remember.remember.ui.notifications.adapter.RecyclerViewAdapterMemories
import com.remember.remember.ui.notifications.adapter.RecyclerViewAdapterMemoryLineActivities
import com.remember.remember.ui.notifications.adapter.RecyclerViewAdapterInvites
import com.remember.remember.ui.notifications.widgets.RecyclerViewSeeMoreItems
import com.remember.repository.model.invite.Invite
import com.remember.repository.model.notification.Memories
import com.remember.repository.model.notification.MemoryLineActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment(R.layout.app_fragment_home_notifications) {

    private val notificationsViewModel: NotificationsViewModel by viewModel()

    private val root: ViewGroup by viewProvider(R.id.root)
    private val memories: RecyclerViewSeeMoreItems by viewProvider(R.id.memories)
    private val invites: RecyclerViewSeeMoreItems by viewProvider(R.id.invites)
    private val activities: RecyclerViewSeeMoreItems by viewProvider(R.id.activities)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_notification)
    private val errorView: ErrorView by viewProvider(R.id.error_view)

    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1
    private val stateError = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewState()
        errorView.setOnClickListener {
            notifications()
        }
    }

    override fun onStart() {
        super.onStart()
        setStatusBar(R.color.statusBarColor)
        notifications()
    }

    private fun setupViewState() {
        viewStateMachine.setup {
            config {
                initialState = stateLoading
            }

            state(stateLoading) {
                visibles(shimmer)
                gones(memories, invites, activities, errorView)
            }

            state(stateData) {
                gones(shimmer, errorView)
                visibles(memories, invites, activities)
            }

            state(stateError) {
                visibles(errorView)
                gones(memories, invites, activities, shimmer)
            }
        }
    }

    private fun notifications() {
        notificationsViewModel.notification().observe(viewLifecycleOwner) {
            data {
                setupActivities(it.memoryLineActivities)
                setupInvites(it.invites)
                setupMemories(it.memories)
                viewStateMachine.changeState(stateData)
            }
            showLoading {
                viewStateMachine.changeState(stateLoading)
            }
            error { _ ->
                viewStateMachine.changeState(stateError)
            }
        }
    }

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        super.onDestroyView()
    }

    private fun setupMemories(memoriesList: List<Memories>) {
        memories.setupRecycler(RecyclerViewAdapterMemories(memoriesList), 0)
    }

    private fun setupActivities(activitiesList: List<MemoryLineActivity>) {
        activities.setupRecycler(RecyclerViewAdapterMemoryLineActivities(activitiesList), 0)
    }

    private fun setupInvites(invitesList: List<Invite>) {
        val onPositive: OnClickPositive = {
            notificationsViewModel.answerInvite(it.id, true)
                .observe(viewLifecycleOwner) {
                    data {
                        showSuccess(R.string.app_invite_accept, root)
                        notifications()
                    }
                    error { _ ->
                        showError(R.string.app_invite_accept_error, root)
                        notifications()
                    }
                }
        }
        val onNegative: OnClickNegative = {
            notificationsViewModel.answerInvite(it.id, false)
                .observe(viewLifecycleOwner) {
                    data {
                        showSuccess(R.string.app_invite_refused, root)
                        notifications()
                    }
                    error { _ ->
                        showError(R.string.app_invite_refused_error, root)
                        notifications()
                    }
                }
        }
        val adapter = RecyclerViewAdapterInvites(invitesList.take(3), onPositive, onNegative)
        invites.setupRecycler(adapter, invitesList.count())
    }
}
