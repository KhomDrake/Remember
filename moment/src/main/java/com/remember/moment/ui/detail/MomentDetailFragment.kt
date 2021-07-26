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
import com.bumptech.glide.Glide
import com.remember.common.bottomsheet.CustomBottomSheetDialog
import com.remember.common.extension.showError
import com.remember.extension.navControllerProvider
import com.remember.extension.setStatusBar
import com.remember.extension.setStatusBarIconsLight
import com.remember.moment.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MomentDetailFragment : Fragment(R.layout.moment_fragment_moment_detail) {

    private val args: MomentDetailFragmentArgs by navArgs()
    private val momentDetailViewModel: MomentDetailViewModel by viewModel()
    private val root: ViewGroup by viewProvider(R.id.root)
    private val navController: NavController by navControllerProvider()
    private val toolbar: AppCompatImageView by viewProvider(R.id.toolbar)
    private val deleteMoment: AppCompatTextView by viewProvider(R.id.delete_moment)
    private val momentImage: AppCompatImageView by viewProvider(R.id.moment_image)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBar(R.color.remember_black)
        setStatusBarIconsLight()
        Glide.with(this).load(args.urlMoment).into(momentImage)
        setupViewModel()
        setupDeleteMoment()
        setupToolbar()
    }

    private fun setupViewModel() {
        momentDetailViewModel.setIdMoment(args.idMoment)
        momentDetailViewModel.setIsOwner(args.isOwner)
    }

    private fun setupToolbar() {
        toolbar.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun setupDeleteMoment() {
        deleteMoment.setOnClickListener {
            deleteMomentBottomSheet()
        }
        momentDetailViewModel.isOwner.observeSingle(viewLifecycleOwner) {
            deleteMoment.isClickable = it
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
                        this@MomentDetailFragment.requireActivity().finish()
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
