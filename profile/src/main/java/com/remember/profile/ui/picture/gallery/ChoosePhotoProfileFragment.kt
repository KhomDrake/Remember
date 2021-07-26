package com.remember.profile.ui.picture.gallery

import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.remember.extension.compress
import com.remember.extension.navControllerProvider
import com.remember.gallery.base.BaseChoosePhotoFragment
import com.remember.gallery.model.Image
import com.remember.repository.model.Album
import java.io.File

class ChoosePhotoProfileFragment : BaseChoosePhotoFragment() {

    private val args: ChoosePhotoProfileFragmentArgs by navArgs()
    private val navController: NavController by navControllerProvider()

    override val album: Album
        get() = args.album

    override fun onToolbarBackIcon() {
        navController.navigateUp()
    }

    override fun onClickImage(image: Image) {
        image.data?.let { data ->
            val file = File(data).compress(requireContext())
            navController.navigate(
                ChoosePhotoProfileFragmentDirections.actionChoosePhotoProfileFragmentToPreviewProfilePictureFragment(
                    args.profileId, file
                )
            )
        } ?: notPossibleToSelectPhoto()
    }
}
