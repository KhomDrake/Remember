package com.remember.moment.ui.detail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.extension.showError
import com.remember.common.extension.showSuccess
import com.remember.common.widget.ErrorView
import com.remember.common.widget.EventEditText
import com.remember.common.widget.RememberToolbar
import com.remember.extension.navControllerProvider
import com.remember.extension.setStatusBar
import com.remember.extension.setStatusBarIconsLight
import com.remember.extension.onScroll
import com.remember.moment.R
import com.remember.repository.model.comment.Comment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MomentCommentsFragment : Fragment(R.layout.moment_fragment_moment_comments) {

    private val args: MomentCommentsFragmentArgs by navArgs()
    private val momentCommentsViewModel: MomentCommentsViewModel by viewModel()
    private val root: ViewGroup by viewProvider(R.id.root)
    private val navController: NavController by navControllerProvider()
    private val toolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val comments: RecyclerView by viewProvider(R.id.comments)
    private val sendComment: EventEditText by viewProvider(R.id.comment)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer)
    private val errorView: ErrorView by viewProvider(R.id.retrievable_error)
    private val linearLayoutManager = LinearLayoutManager(
        context,
        LinearLayoutManager.VERTICAL,
        false
    )
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1
    private val stateError = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewStateMachine()
        momentCommentsViewModel.setIdMoment(args.idMoment)
        momentCommentsViewModel.reset()
        setupToolbar()
        comments()
        setupSendComment()
        setupRetrievableError()
        setStatusBar(R.color.statusBarColor)
        setStatusBarIconsLight()

        comments.onScroll({lastVisibleItemPosition}) {
            when {
                comments.commentsAdapter.hasNoMore -> Unit
                momentCommentsViewModel.hasNextPage.not() -> {
                    comments.commentsAdapter.addNoMore()
                }
                comments.commentsAdapter.canLoadMore.not() -> Unit
                else -> nextPage()
            }
        }
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
                gones(comments, sendComment, errorView)
            }

            state(stateData) {
                visibles(comments, sendComment)
                gones(shimmer, errorView)
            }

            state(stateError) {
                visibles(errorView)
                gones(comments, sendComment, shimmer)
            }
        }
    }

    private fun setupToolbar() {
        toolbar.setOnClickListenerBackIcon {
            navController.navigateUp()
        }
    }

    private fun setupRetrievableError() {
        errorView.setOnClickListener {
            comments(firstTime = true)
        }
    }

    private fun nextPage() {
        momentCommentsViewModel.comments()
            .observe(viewLifecycleOwner) {
                data {
                    momentCommentsViewModel.setNextPage(it.next)
                    comments.commentsAdapter.addData(it.results)
                }
                showLoading {
                    comments.commentsAdapter.addLoading()
                }
                error { _ ->
                    comments.commentsAdapter.addError()
                }
            }
    }

    private fun setupSendComment() {
        sendComment.whenShowInputBasedOnText { it.isNotEmpty() }
        sendComment.setPaddingSearch()
        sendComment.onEventClick {
            val comment = sendComment.text
            if(momentCommentsViewModel.notValidComment(comment)) {
                showError(R.string.moment_send_empty_comment, root)
                return@onEventClick
            }

            momentCommentsViewModel.insertComment(comment)
                .observe(viewLifecycleOwner) {
                    data {
                        sendComment.resetValue()
                        showSuccess(R.string.moment_send_comment_success, root)
                        comments(firstTime = true)
                    }
                    showLoading {
                        sendComment.onLoading(true)
                    }
                    loading {
                        sendComment.onLoading(it)
                    }
                    error { _ ->
                        sendComment.onLoading(false)
                        showError(R.string.moment_send_comment_error, root)
                    }
                }
        }
    }

    private fun comments(firstTime: Boolean = false) {
        if(firstTime) momentCommentsViewModel.reset()

        momentCommentsViewModel.comments()
            .observe(viewLifecycleOwner) {
                data {
                    momentCommentsViewModel.setNextPage(it.next)
                    setupAdapter(it.results)
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

    private fun setupAdapter(c: List<Comment>) {
        comments.layoutManager = linearLayoutManager
        comments.adapter = RecyclerViewAdapterComments()
        comments.commentsAdapter.addData(c)
        comments.commentsAdapter.setOnError {
            nextPage()
        }
    }

    private val RecyclerView.commentsAdapter: RecyclerViewAdapterComments
        get() = adapter as RecyclerViewAdapterComments
}
