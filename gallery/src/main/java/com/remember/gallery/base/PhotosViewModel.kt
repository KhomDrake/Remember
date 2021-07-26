package com.remember.gallery.base

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.remember.gallery.model.Image
import com.remember.repository.model.Album
import com.remember.repository.operation.asyncLiveData

class PhotosViewModel : ViewModel() {

    fun photosByAlbum(context: Context, album: Album) = asyncLiveData {
        val images = mutableListOf<Image>()
        val imagesProjection = arrayOf(MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.DATA)
        val imagesQueryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val queryImages = context.contentResolver.query(
            imagesQueryUri,
            imagesProjection,
            "${MediaStore.Images.Media.BUCKET_ID} = ${album.id}",
            null,
            "${MediaStore.Images.Media.DATE_ADDED} desc"
        )

        if (queryImages != null && queryImages.moveToFirst()) {
            do {
                val data = queryImages.getString(queryImages.getColumnIndex(MediaStore.Images.Media.DATA))
                images.add(Image(data))
            } while (queryImages.moveToNext())
        }
        queryImages?.close()
        images
    }
}
