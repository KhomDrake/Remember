package com.remember.memoryline.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.remember.common.fragment.AddParticipantsFragment
import com.remember.extension.navControllerProvider
import com.remember.repository.model.search.UserSearch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreateMemoryLineAddParticipantFragment : AddParticipantsFragment() {

    private val navController: NavController by navControllerProvider()
    private val createMemoryLineViewModel: CreateMemoryLineViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createMemoryLineViewModel.setupParticipantSelected()
    }

    override fun setupObservables() {
        createMemoryLineViewModel.participantsSelected.observe(viewLifecycleOwner, Observer {
            it?.let {
                setupChosenParticipantsRecyclerView(it)
            }
        })
    }

    override fun onSearch() {
        createMemoryLineViewModel.search().observe(viewLifecycleOwner) {
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
        createMemoryLineViewModel.username = value
    }

    override fun onBackToolbar() {
        navController.navigateUp()
    }

    override fun onConfirm() {
        createMemoryLineViewModel.setParticipantChosen()
        navController.navigateUp()
    }

    override fun onRemoveParticipant(userSearch: UserSearch) {
        createMemoryLineViewModel.removeParticipant(userSearch)
    }

    override fun onAddParticipant(userSearch: UserSearch) {
        createMemoryLineViewModel.addParticipant(userSearch)
    }
}
