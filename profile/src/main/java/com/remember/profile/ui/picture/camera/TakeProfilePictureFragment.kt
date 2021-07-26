package com.remember.profile.ui.picture.camera

import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.remember.camera.ui.BaseCameraFragment
import com.remember.extension.navControllerProvider
import java.io.File

class TakeProfilePictureFragment : BaseCameraFragment(beginWithFrontCamera = true) {

    private val navController: NavController by navControllerProvider()
    private val args: TakeProfilePictureFragmentArgs by navArgs()

    override fun onTakePicture(compressedFile: File) {
        navController.navigate(
            TakeProfilePictureFragmentDirections.actionTakeProfilePictureFragmentToPreviewProfilePictureFragment(
                args.profileId, compressedFile
            )
        )
    }
}
