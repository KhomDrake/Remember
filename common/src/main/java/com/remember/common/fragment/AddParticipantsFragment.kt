package com.remember.common.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.R
import com.remember.common.adapters.RecyclerViewAdapterChosenParticipants
import com.remember.common.adapters.RecyclerViewAdapterParticipant
import com.remember.common.widget.EventEditText
import com.remember.common.widget.RememberToolbar
import com.remember.extension.setStatusBar
import com.remember.repository.model.search.UserSearch

abstract class AddParticipantsFragment : Fragment(R.layout.common_add_participant_fragment) {

    private val users: RecyclerView by viewProvider(R.id.users)
    private val usersAdded: RecyclerView by viewProvider(R.id.users_added)
    private val confirm: AppCompatButton by viewProvider(R.id.confirm)
    private val shimmerSearch: ShimmerFrameLayout by viewProvider(R.id.shimmer_add_participant_search)
    protected val searchUsername: EventEditText by viewProvider(R.id.search_username)
    protected val root: ViewGroup by viewProvider(R.id.root)
    protected val toolbar: RememberToolbar by viewProvider(R.id.toolbar)
    protected val viewStateMachine = ViewStateMachine()
    protected val stateLoadingSearch = 0
    protected val stateDataSearch = 1

    protected open val buttonText: Int = R.string.common_add_participant_default_button_text

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        users.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        usersAdded.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        confirm.text = getString(buttonText)
        setStatusBar(R.color.statusBarColor)
        setupListeners()
        setupObservables()
        setupViewStateMachine()
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            state(stateLoadingSearch) {
                visibles(shimmerSearch)
                gones(users)
            }

            state(stateDataSearch) {
                gones(shimmerSearch)
                visibles(users)
            }
        }
    }

    abstract fun setupObservables()

    abstract fun onSearch()

    abstract fun onTextChanged(value: String)

    private fun setupListeners() {
        searchUsername.whenShowInputBasedOnText { text -> text.length > 2 }
        searchUsername.onEventClick {
            onSearch()

        }
        searchUsername.setPaddingSearch()
        searchUsername.onTextChanged { onTextChanged(it) }
        confirm.setOnClickListener {
            onConfirm()
        }
        toolbar.setOnClickListenerBackIcon {
            onBackToolbar()
        }
    }

    abstract fun onBackToolbar()

    abstract fun onConfirm()

    protected fun setupChosenParticipantsRecyclerView(participants: List<UserSearch>) {
        usersAdded.adapter =
            RecyclerViewAdapterChosenParticipants(
                participants.toMutableList()
            ) {
                onRemoveParticipant(it)
            }
    }

    abstract fun onRemoveParticipant(userSearch: UserSearch)

    abstract fun onAddParticipant(userSearch: UserSearch)

    protected fun setupParticipantsRecyclerView(participants: List<UserSearch>) {
        users.adapter =
            RecyclerViewAdapterParticipant(
                participants
            ) {
                onAddParticipant(it)
            }
    }

    override fun onDestroyView() {
        viewStateMachine.shutdown()
        super.onDestroyView()
    }
}
