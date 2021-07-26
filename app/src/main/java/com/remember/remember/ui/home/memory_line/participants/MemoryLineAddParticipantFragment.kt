package com.remember.remember.ui.home.memory_line.participants

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.remember.common.LoadingDialog
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.common.fragment.AddParticipantsFragment
import com.remember.extension.navControllerProvider
import com.remember.remember.R
import com.remember.repository.model.search.UserSearch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoryLineAddParticipantFragment : AddParticipantsFragment() {

    private val memoryLineAddParticipantViewModel: MemoryLineAddParticipantViewModel by viewModel()
    private val navController: NavController by navControllerProvider()
    private val navArgs: MemoryLineAddParticipantFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        memoryLineAddParticipantViewModel.setIdMemoryLine(navArgs.idMemoryLine)
        super.onViewCreated(view, savedInstanceState)
    }

    override val buttonText: Int
        get() = R.string.app_invite_participants_button_text

    override fun setupObservables() {
        memoryLineAddParticipantViewModel.participantsSelected.observe(viewLifecycleOwner, Observer {
            it?.let {
                setupChosenParticipantsRecyclerView(it)
            }
        })
    }

    override fun onSearch() {
        memoryLineAddParticipantViewModel.search().observe(viewLifecycleOwner) {
            data {
                setupParticipantsRecyclerView(it.results)
                viewStateMachine.changeState(stateDataSearch)
            }
            showLoading {
                viewStateMachine.changeState(stateLoadingSearch)
            }
            loading {
                searchUsername.onLoading(it)
            }
        }
    }

    override fun onTextChanged(value: String) {
        memoryLineAddParticipantViewModel.username = value
    }

    override fun onBackToolbar() {
        navController.navigateUp()
    }

    override fun onConfirm() {
        val participantsSelected = memoryLineAddParticipantViewModel.participantsSelected.value

        if (participantsSelected == null || participantsSelected.isEmpty()) {
            showError(R.string.app_add_participant_not_user_selected, root)
            return
        } else {
            LoadingDialog(memoryLineAddParticipantViewModel.createInvites(participantsSelected),
                onData = {
                    showSuccess(R.string.app_successfully_invited, root)
                    navController.navigateUp()
                },
                onError = {
                    showError(R.string.app_failure_to_create_invite, root)
                }).show(parentFragmentManager, null)
        }
    }

    override fun onRemoveParticipant(userSearch: UserSearch) {
        memoryLineAddParticipantViewModel.removeParticipant(userSearch)
    }

    override fun onAddParticipant(userSearch: UserSearch) {
        memoryLineAddParticipantViewModel.addParticipant(userSearch)
    }
}
