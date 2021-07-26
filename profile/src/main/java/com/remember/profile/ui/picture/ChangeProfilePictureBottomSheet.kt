package com.remember.profile.ui.picture

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentManager
import br.com.arch.toolkit.delegate.viewProvider
import com.remember.common.bottomsheet.RoundedBottomSheetDialogFragment
import com.remember.profile.R

typealias ChooseRemovePicture = (ChangeProfilePictureBottomSheet) -> Unit
typealias ChooseGallery = (ChangeProfilePictureBottomSheet) -> Unit
typealias ChooseCamera = (ChangeProfilePictureBottomSheet) -> Unit

class ChangeProfilePictureBottomSheet : RoundedBottomSheetDialogFragment(R.layout.profile_change_profile_picture_bottom_sheet, canDismiss = true) {

    private val deleteIcon: AppCompatImageView by viewProvider(R.id.delete_icon)
    private val galleryIcon: AppCompatImageView by viewProvider(R.id.gallery_icon)
    private val cameraIcon: AppCompatImageView by viewProvider(R.id.camera_icon)

    private var chooseRemovePicture: ChooseRemovePicture = {}
    private var chooseGallery: ChooseGallery = {}
    private var chooseCamera: ChooseCamera = {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deleteIcon.setOnClickListener {
            chooseRemovePicture.invoke(this)
        }
        galleryIcon.setOnClickListener {
            chooseGallery.invoke(this)
        }
        cameraIcon.setOnClickListener {
            chooseCamera.invoke(this)
        }
    }

    class Builder {
        private var chooseRemovePicture: ChooseRemovePicture = {}
        private var chooseGallery: ChooseGallery = {}
        private var chooseCamera: ChooseCamera = {}

        fun setChooseRemovePicture(choose: ChooseRemovePicture) {
            chooseRemovePicture = choose
        }

        fun setChooseGallery(choose: ChooseGallery) {
            chooseGallery = choose
        }

        fun setChooseCamera(choose: ChooseCamera) {
            chooseCamera = choose
        }

        fun show(fragmentManager: FragmentManager, tag: String? = ChangeProfilePictureBottomSheet::class.java.name) {
            val changeProfilePictureBottomSheet =
                ChangeProfilePictureBottomSheet()
            changeProfilePictureBottomSheet.chooseRemovePicture = chooseRemovePicture
            changeProfilePictureBottomSheet.chooseGallery = chooseGallery
            changeProfilePictureBottomSheet.chooseCamera = chooseCamera
            changeProfilePictureBottomSheet.show(fragmentManager, tag)
        }

    }
}