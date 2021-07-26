package com.remember.moment.ui.create.gallery

import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.remember.extension.navControllerProvider
import com.remember.gallery.base.BaseChooseAlbumsFragment
import com.remember.repository.model.Album

class MomentChooseAlbumFragment : BaseChooseAlbumsFragment() {

    private val navController: NavController by navControllerProvider()
    private val args: MomentChooseAlbumFragmentArgs by navArgs()

    override fun onToolbarBackIcon() {
        requireActivity().finish()
    }

    override fun onClickAlbum(album: Album) {
        navController.navigate(
            MomentChooseAlbumFragmentDirections.choosePhoto(args.memoryLineData, album)
        )
    }
}
