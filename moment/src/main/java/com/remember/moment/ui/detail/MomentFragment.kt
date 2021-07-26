package com.remember.moment.ui.detail

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.livedata.extention.observeSingle
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.config
import br.com.arch.toolkit.statemachine.setup
import br.com.arch.toolkit.statemachine.state
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.remember.common.bottomsheet.CustomBottomSheetDialog
import com.remember.common.extension.showError
import com.remember.common.widget.Avatar
import com.remember.common.widget.ErrorView
import com.remember.extension.navControllerProvider
import com.remember.extension.setStatusBar
import com.remember.extension.setStatusBarIconsLight
import com.remember.extension.toFormattedString
import com.remember.extension.toLocalDate
import com.remember.moment.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MomentFragment : Fragment(R.layout.moment_fragment_moment) {

    private val momentDetailViewModel: MomentDetailViewModel by viewModel()
    private val args: MomentFragmentArgs by navArgs()
    private val navController: NavController by navControllerProvider()

    private val toolbar: AppCompatImageView by viewProvider(R.id.toolbar)
    private val deleteMoment: AppCompatTextView by viewProvider(R.id.delete_moment)
    private val root: ViewGroup by viewProvider(R.id.root)
    private val momentImage: AppCompatImageView by viewProvider(R.id.moment_image)
    private val ownerMoment: Avatar by viewProvider(R.id.owner_moment)
    private val date: AppCompatTextView by viewProvider(R.id.date)
    private val description: AppCompatTextView by viewProvider(R.id.description)
    private val comments: AppCompatTextView by viewProvider(R.id.comments)
    private val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer_moment_detail)
    private val errorView: ErrorView by viewProvider(R.id.error_view)

    private val viewStateMachine = ViewStateMachine()
    private val stateLoading = 0
    private val stateData = 1
    private val stateError = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBar(R.color.statusBarColor)
        setStatusBarIconsLight()
        setupViewStateMachine()
        setupToolbar()
        setupMenu()
        momentDetailViewModel.setIdMoment(args.idMoment)
        momentImage.clipToOutline = true
        errorView.setOnClickListener {
            loadMomentData()
        }
    }

    override fun onStart() {
        super.onStart()
        loadMomentData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateMachine.shutdown()
    }

    private fun loadMomentData() {
        momentDetailViewModel.detail().observe(viewLifecycleOwner) {
            data {
                Glide.with(this@MomentFragment).load(it.urlBucket).into(momentImage)
                ownerMoment.setImage(it.owner.photo)
                description.text = it.description
                momentImage.setOnClickListener { _ ->
                    navController.navigate(
                        MomentFragmentDirections.actionMomentFragmentToMomentDetailFragment(
                            it.urlBucket,
                            it.idMoment,
                            momentDetailViewModel.isOwner.value ?: false
                        )
                    )
                }
                momentDetailViewModel.setIsOwner(it.owner.id)
                comments.text = requireContext().resources.getQuantityString(R.plurals.moment_comments_quantity, it.commentsCount, it.commentsCount)
                comments.setOnClickListener { _ ->
                    navController.navigate(
                        MomentFragmentDirections.actionMomentFragmentToMomentCommentsFragment(
                            it.idMoment
                        )
                    )
                }
                date.text = it.creationDate.toLocalDate().toFormattedString()
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

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            config {
                initialState = stateLoading
            }

            state(stateLoading) {
                visibles(shimmer)
                gones(momentImage, ownerMoment, date, description, comments, errorView)
            }

            state(stateData) {
                visibles(momentImage, ownerMoment, date, description, comments)
                gones(shimmer, errorView)
            }

            state(stateError) {
                visibles(errorView)
                gones(shimmer, momentImage, ownerMoment, date, description, comments)
            }
        }
    }

    private fun setupToolbar() {
        toolbar.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun setupMenu() {
        deleteMoment.setOnClickListener {
            deleteMomentBottomSheet()
        }

        momentDetailViewModel.isOwner.observeSingle(viewLifecycleOwner) {
            deleteMoment.isClickable = it
            deleteMoment.isEnabled = it
            deleteMoment.alpha = if(it) 1f else .5f
        }
    }

    private fun deleteMomentBottomSheet() {
        CustomBottomSheetDialog.Builder().apply {
            setTitle(R.string.moment_delete_moment_title)
            setMessage(R.string.moment_delete_moment_message)
            setPositiveButtonText(R.string.moment_delete_moment_positive_button_text)
            setNegativeButtonText(R.string.moment_delete_moment_negative_button_text)
            onClickPositiveButton { _, customBottomSheet ->
                customBottomSheet.dismiss()
            }
            onClickNegativeButton { button, customBottomSheet ->
                momentDetailViewModel.deleteMoment().observe(viewLifecycleOwner) {
                    success {
                        this@MomentFragment.requireActivity().finish()
                        customBottomSheet.dismiss()
                    }
                    showLoading {
                        button.onLoading(true)
                    }
                    error { _ ->
                        showError(R.string.moment_failed_to_delete_moment, root)
                        button.onLoading(false)
                    }
                }
            }
        }.show(parentFragmentManager)
    }
}
