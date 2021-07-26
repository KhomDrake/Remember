package com.remember.remember.ui.home.memory_line.configuration

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.actions.toHome
import com.remember.common.actions.toProfile
import com.remember.common.bottomsheet.CustomBottomSheetDialog
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.common.widget.ErrorView
import com.remember.common.widget.ImageTextView
import com.remember.common.widget.RememberToolbar
import com.remember.extension.navControllerProvider
import com.remember.extension.setStatusBar
import com.remember.remember.BuildConfig
import com.remember.remember.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemoryLineConfigurationFragment : Fragment(R.layout.app_fragment_memory_line_configuration) {

    private val memoryLineConfigurationViewModel: MemoryLineConfigurationViewModel by viewModel()
    private val navController: NavController by navControllerProvider()
    private val navArgs: MemoryLineConfigurationFragmentArgs by navArgs()

    private val root: ViewGroup by viewProvider(R.id.root)
    private val rememberToolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val memoryLineName: AppCompatTextView by viewProvider(R.id.memory_line_name)
    private val editMemoryLineName: AppCompatImageView by viewProvider(R.id.edit_memory_line_name)
    private val addParticipant: ImageTextView by viewProvider(R.id.add_participant)
    private val quantityParticipants: ImageTextView by viewProvider(R.id.quantity_participants)
    private val leaveMemoryLine: ImageTextView by viewProvider(R.id.leave_memory_line)
    private val deleteMemoryLine: ImageTextView by viewProvider(R.id.delete_memory_line)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_configuration)
    private val errorView: ErrorView by viewProvider(R.id.error_view)
    private val line: View by viewProvider(R.id.line)

    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1
    private val stateError = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setStatusBar(R.color.statusBarColor)
        setupMemoryLineInformation()
        setupViewStateMachine()
        setupListeners()
        setupRetrievableError()
        setupToolbar()
    }

    override fun onStart() {
        super.onStart()
        setupObservables()
        setupProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoading
            }

            state(stateLoading) {
                visibles(shimmer)
                gones(memoryLineName, editMemoryLineName, addParticipant, line, quantityParticipants, deleteMemoryLine, leaveMemoryLine, errorView)
            }

            state(stateData) {
                visibles(memoryLineName, editMemoryLineName, addParticipant, line, quantityParticipants, deleteMemoryLine, leaveMemoryLine)
                gones(shimmer, errorView)
                onEnter {
                    leaveMemoryLine.isVisible = BuildConfig.SHOW_LEAVE_MEMORY_LINE
                    updateMemoryLineData()
                }
            }

            state(stateError) {
                visibles(errorView)
                gones(shimmer, memoryLineName, editMemoryLineName, addParticipant, line, quantityParticipants, deleteMemoryLine, leaveMemoryLine)
            }
        }
    }

    private fun setupProfile() {
        memoryLineConfigurationViewModel.profile().observe(viewLifecycleOwner) {
            data {
                rememberToolbar.loadAvatar(it.photo)
            }
            error { _ ->
                rememberToolbar.loadAvatar(null)
            }
        }
    }

    private fun setupToolbar() {
        rememberToolbar.setAvatarVisibility(true)
        rememberToolbar.setOnClickListenerBackIcon {
            navController.navigateUp()
        }

        rememberToolbar.setOnClickListenerAvatarIcon {
            startActivity(requireContext().toProfile())
        }
    }

    private fun setupRetrievableError() {
        errorView.setOnClickListener {
            setupObservables()
        }
    }

    private fun setupMemoryLineInformation() {
        memoryLineConfigurationViewModel.setMemoryLineInformation(navArgs.memoryLineData)
    }

    private fun updateMemoryLineData() {
        memoryLineConfigurationViewModel.memoryLineBaseInformation.observe(viewLifecycleOwner, Observer {
            if(it != null) memoryLineName.text = it.name
            deleteMemoryLine.isVisible = it.ownerId == memoryLineConfigurationViewModel.userId()
        })
    }

    private fun setupListeners() {
        quantityParticipants.setOnClickListener {
            navController.navigate(
                MemoryLineConfigurationFragmentDirections.actionMemoryLineConfigurationFragmentToMemoryLineParticipantsFragment(
                    memoryLineConfigurationViewModel.idMemoryLine
                )
            )
        }

        addParticipant.setOnClickListener {
            navController.navigate(
                MemoryLineConfigurationFragmentDirections.actionMemoryLineConfigurationFragmentToMemoryLineAddParticipantFragment(
                    memoryLineConfigurationViewModel.idMemoryLine
                )
            )
        }

        editMemoryLineName.setOnClickListener {
            CustomBottomSheetDialog.Builder().apply {
                onClickNegativeButton { _, customBottomSheet ->
                    customBottomSheet.dismiss()
                }

                setTitle(R.string.app_change_memory_line_name_title)
                setInputHint(R.string.app_change_memory_line_name_hint)
                setPositiveButtonText(R.string.app_change_memory_line_name_positive_button)
                setNegativeButtonText(R.string.app_change_memory_line_name_negative_button)

                setDefaultValueInput(memoryLineConfigurationViewModel.nameMemoryLine)

                onClickPositiveButton { button, customBottomSheet ->
                    if (memoryLineConfigurationViewModel.nameMemoryLine == customBottomSheet.inputValue) {
                        showError(R.string.app_memory_line_name_equal, root)
                        return@onClickPositiveButton
                    }
                    if (customBottomSheet.inputValue.isEmpty()) {
                        showError(R.string.app_memory_line_name_empty, root)
                        return@onClickPositiveButton
                    }

                    memoryLineConfigurationViewModel.updateMemoryLineName(customBottomSheet.inputValue)
                        .observe(viewLifecycleOwner) {
                            data {
                                memoryLineConfigurationViewModel.setNameMemoryLine(customBottomSheet.inputValue)
                                showSuccess(R.string.app_memory_line_name_success, root)
                                customBottomSheet.dismiss()
                            }
                            showLoading {
                                button.onLoading(true)
                            }
                            error { _ ->
                                button.onLoading(false)
                                showError(R.string.app_memory_line_name_error, root)
                            }
                        }
                }
            }.show(parentFragmentManager)
        }

        leaveMemoryLine.setOnClickListener {}

        deleteMemoryLine.setOnClickListener {
            CustomBottomSheetDialog.Builder().apply {

                onClickNegativeButton { button, customBottomSheet ->
                    memoryLineConfigurationViewModel.deleteMemoryLine().observe(viewLifecycleOwner) {
                        data {
                            showSuccess(R.string.app_memory_line_delete_success, root)
                            startActivity(requireContext().toHome())
                            customBottomSheet.dismiss()
                        }
                        showLoading {
                            button.onLoading(true)
                        }
                        error { _ ->
                            button.onLoading(false)
                            showError(R.string.app_memory_line_delete_error, root)
                        }
                    }
                }

                setTitle(R.string.app_delete_memory_line_title)
                setMessage(R.string.app_delete_memory_line_message)
                setPositiveButtonText(R.string.app_delete_memory_line_positive_button)
                setNegativeButtonText(R.string.app_delete_memory_line_negative_button)

                onClickPositiveButton { _, customBottomSheet ->
                    customBottomSheet.dismiss()
                }

            }.show(parentFragmentManager)
        }
    }

    private fun setupObservables() {
        memoryLineConfigurationViewModel.participants()
            .observe(viewLifecycleOwner) {
                data {
                    quantityParticipants.setText(getString(R.string.app_quantity_participants, it.results.count()))
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
}
