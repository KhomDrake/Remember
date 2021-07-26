package com.remember.gallery

import com.remember.common.initializer.Modules
import com.remember.gallery.base.AlbumsViewModel
import com.remember.gallery.base.PhotosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class GalleryModule : Modules() {
    override fun loadModule() = listOf(module {
        viewModel { AlbumsViewModel() }
        viewModel { PhotosViewModel() }
        viewModel { GalleryViewModel() }
    })
}
