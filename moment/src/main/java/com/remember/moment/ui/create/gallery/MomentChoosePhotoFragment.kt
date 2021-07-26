package com.remember.moment.ui.create.gallery

import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.remember.extension.compress
import com.remember.extension.navControllerProvider
import com.remember.gallery.base.BaseChoosePhotoFragment
import com.remember.gallery.model.Image
import com.remember.repository.model.Album
import java.io.File

class MomentChoosePhotoFragment : BaseChoosePhotoFragment() {

    private val navController: NavController by navControllerProvider()
    private val args: MomentChoosePhotoFragmentArgs by navArgs()

    override val album: Album
        get() = args.album

    override fun onToolbarBackIcon() {
        navController.navigateUp()
    }

    override fun onClickImage(image: Image) {
        val file = File(image.data ?: kotlin.run {
            requireActivity().finish()
            return
        })
        val fileCompress = file.compress(requireContext())
        navController.navigate(
            MomentChoosePhotoFragmentDirections.goToPreviewPhoto(args.memoryLineData, fileCompress)
        )
    }
}