package com.remember.gallery

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.remember.common.actions.ACTION
import com.remember.common.actions.IS_FOR_RESULT
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryActivity : AppCompatActivity(R.layout.gallery_activity_gallery) {

    private val galleryViewModel: GalleryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        intent.extras?.let {
            galleryViewModel.isForResult = it.getBoolean(IS_FOR_RESULT, true)
            galleryViewModel.action = it.getString(ACTION)
        }
    }
}
