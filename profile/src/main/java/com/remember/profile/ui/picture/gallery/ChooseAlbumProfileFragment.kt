package com.remember.profile.ui.picture.gallery

import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.remember.extension.navControllerProvider
import com.remember.gallery.base.BaseChooseAlbumsFragment
import com.remember.repository.model.Album

class ChooseAlbumProfileFragment : BaseChooseAlbumsFragment() {

    private val navController: NavController by navControllerProvider()
    private val args: ChooseAlbumProfileFragmentArgs by navArgs()

    override fun onToolbarBackIcon() {
        navController.navigateUp()
    }

    override fun onClickAlbum(album: Album) {
        navController.navigate(
            ChooseAlbumProfileFragmentDirections.actionChooseAlbumProfileFragmentToChoosePhotoProfileFragment(
                args.profileId, album
            )
        )
    }
}
