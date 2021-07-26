package com.remember.gallery

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.remember.extension.navControllerProvider
import com.remember.gallery.base.BaseChoosePhotoFragment
import com.remember.gallery.model.Image
import com.remember.repository.model.Album
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

const val GALLERY_REQUEST_CODE = 1729
const val GALLERY_REQUEST_EXTRA = "GALLERY_REQUEST_EXTRA"

class PhotosFragment : BaseChoosePhotoFragment() {

    private val navController: NavController by navControllerProvider()
    private val galleryViewModel: GalleryViewModel by sharedViewModel()
    private val photosFragmentArgs: PhotosFragmentArgs by navArgs()
    override val album: Album
        get() = photosFragmentArgs.album

    override fun onToolbarBackIcon() {
        navController.navigateUp()
    }

    override fun onClickImage(image: Image) {
        if(galleryViewModel.isForResult) {
            requireActivity().setResult(
                GALLERY_REQUEST_CODE,
                Intent().putExtra(GALLERY_REQUEST_EXTRA, image.data)
            )
            requireActivity().finish()
        } else {
            requireContext().startActivity(Intent().setAction(galleryViewModel.action).apply {
                putExtra(GALLERY_REQUEST_EXTRA, image.data)
            })
        }
    }
}
