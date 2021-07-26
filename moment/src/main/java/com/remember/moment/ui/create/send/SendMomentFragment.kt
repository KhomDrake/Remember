package com.remember.moment.ui.create.send

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.remember.common.actions.MEMORY_LINE_ID
import com.remember.common.actions.MEMORY_LINE_NAME
import com.remember.common.actions.OWNER_ID
import com.remember.common.actions.toHome
import com.remember.common.widget.ErrorView
import com.remember.extension.toBase64
import com.remember.moment.R
import com.remember.moment.ui.create.MomentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendMomentFragment : Fragment(R.layout.moment_fragment_send_moment) {

    private val uploadCloudText: AppCompatTextView by viewProvider(R.id.upload_cloud_text)
    private val uploadCloudIcon: AppCompatImageView by viewProvider(R.id.upload_cloud_icon)
    private val errorView: ErrorView by viewProvider(R.id.retrievable_error)
    private val args: SendMomentFragmentArgs by navArgs()
    private val progressBar: ProgressBar by viewProvider(R.id.progress_bar)
    private val momentViewModel: MomentViewModel by viewModel()
    private val viewStateMachine = ViewStateMachine()
    private val stateUpload = 0
    private val stateError = 1
    private val animationUpload = 2500L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewStateMachine()
        createMoment()
        errorView.setOnClickListener {
            createMoment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateUpload
            }

            state(stateUpload) {
                visibles(uploadCloudIcon, uploadCloudText, progressBar)
                gones(errorView)
            }

            state(stateError) {
                gones(uploadCloudIcon, uploadCloudText, progressBar)
                visibles(errorView)
            }
        }
    }

    private fun createMoment() {
        viewStateMachine.changeState(stateUpload)
        lifecycleScope.launch {
            val momentBase64 = withContext(Dispatchers.IO) {
                runCatching { args.file.toBase64() }.onFailure {
                    viewStateMachine.changeState(stateError)
                }.getOrDefault("")
            }

            if(momentBase64.isEmpty()) return@launch

            progressBar.progress = 0

            val animation = ValueAnimator.ofInt(0, 950).apply {
                duration = animationUpload
                addUpdateListener {
                    if(this@SendMomentFragment.isVisible) progressBar.progress = it.animatedValue as Int
                }
            }
            animation.start()

            momentViewModel.insertMemoryLine(
                idMemoryLine = args.memoryLineData.idMemoryLine,
                moment = momentBase64,
                description = momentViewModel.description
            ).observe(viewLifecycleOwner) {
                data {
                    progressBar.progress = 1000
                    animation.cancel()

                    val intent = requireContext().toHome().apply {
                        putExtra(MEMORY_LINE_NAME, args.memoryLineData.name)
                        putExtra(MEMORY_LINE_ID, args.memoryLineData.idMemoryLine)
                        putExtra(OWNER_ID, args.memoryLineData.ownerId)
                    }
                    requireContext().startActivity(intent)
                    requireActivity().finish()
                }
                error { _ ->
                    animation.cancel()
                    viewStateMachine.changeState(stateError)
                }
            }
        }
    }
}
