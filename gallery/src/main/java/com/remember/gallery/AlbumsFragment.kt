package com.remember.gallery

import androidx.navigation.NavController
import com.remember.extension.navControllerProvider
import com.remember.gallery.base.BaseChooseAlbumsFragment
import com.remember.repository.model.Album

const val GALLERY_REQUEST_CODE_NOT_FINISHED = 1728

class AlbumsFragment : BaseChooseAlbumsFragment() {

    private val navController: NavController by navControllerProvider()

    override fun onToolbarBackIcon() {
        requireActivity().setResult(
            GALLERY_REQUEST_CODE_NOT_FINISHED
        )
        requireActivity().finish()
    }

    override fun onClickAlbum(album: Album) {
        navController.navigate(
            AlbumsFragmentDirections.actionAlbumsFragmentToPhotosFragment(
                album
            )
        )
    }
}