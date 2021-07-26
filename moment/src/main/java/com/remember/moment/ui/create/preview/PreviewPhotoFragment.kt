package com.remember.moment.ui.create.preview

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.bottomsheet.CustomBottomSheetDialog
import com.remember.common.widget.Avatar
import com.remember.common.widget.RememberToolbar
import com.remember.extension.navControllerProvider
import com.remember.extension.onTextChanged
import com.remember.moment.R
import com.remember.moment.ui.create.MomentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreviewPhotoFragment: Fragment(R.layout.moment_fragment_preview_photo) {

    private val momentViewModel: MomentViewModel by viewModel()
    private val navController: NavController by navControllerProvider()
    private val args: PreviewPhotoFragmentArgs by navArgs()
    private val moment: AppCompatImageView by viewProvider(R.id.moment)
    private val toolbar: RememberToolbar by viewProvider(R.id.toolbar)
    private val createMoment: AppCompatButton by viewProvider(R.id.add_moment)
    private val profileImage: Avatar by viewProvider(R.id.profile_image)
    private val description: AppCompatEditText by viewProvider(R.id.description)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uri = Uri.fromFile(args.file)
        moment.scaleType = ImageView.ScaleType.CENTER_CROP
        moment.setImageURI(uri)
        moment.clipToOutline = true

        momentViewModel.profileInformation().observeData(viewLifecycleOwner) {
            profileImage.setImage(it.photo)
        }

        toolbar.setOnClickListenerBackIcon {
            navController.navigateUp()
        }

        description.onTextChanged {
            momentViewModel.description = it
        }

        createMoment.setOnClickListener {
            args.memoryLineData?.let { data ->
                CustomBottomSheetDialog.Builder().apply {
                    setTitle(R.string.moment_preview_moment_title_confirm_creation)
                    setPositiveButtonText(R.string.moment_preview_moment_positive_button_text)
                    setNegativeButtonText(R.string.moment_preview_moment_negative_button_text)
                    onClickNegativeButton { _, customBottomSheet ->
                        customBottomSheet.dismiss()
                    }
                    onClickPositiveButton { _, customBottomSheet ->
                        navController.navigate(
                            PreviewPhotoFragmentDirections.sendBucketWithoutChooseMemoryLine(
                                data,
                                args.file
                            )
                        )
                        customBottomSheet.dismiss()
                    }
                }.show(parentFragmentManager)
            } ?: kotlin.run {
                navController.navigate(
                    PreviewPhotoFragmentDirections.chooseMemoryLine(args.file)
                )
            }

        }
    }
}
