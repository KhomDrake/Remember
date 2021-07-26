package com.remember.gallery.base

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.remember.repository.cache.CacheBox
import com.remember.repository.cache.CacheStrategy
import com.remember.repository.model.Album
import com.remember.repository.operation.asyncLiveData

class AlbumsViewModel : ViewModel() {

    fun albums(context: Context) =
        asyncLiveData(
            CacheBox.key,
            CacheBox.albumsHashMap,
            cacheStrategy = CacheStrategy.UPDATE
        ) {
            val al = mutableListOf<Album>()
            val imagesQueryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            var albums: Cursor?
            albums = try {
                val imagesProjection = arrayOf(MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.DATA)
                context.contentResolver.query(imagesQueryUri, imagesProjection, null, null, "${MediaStore.Images.Media.DATE_ADDED} desc")
            } catch (e: Exception) {
                return@asyncLiveData al
            }

            if (albums != null && albums.count > 0 && albums.moveToFirst()) {
                var albumId = ""
                do {
                    val id = albums.getString(albums.getColumnIndex(MediaStore.Images.Media.BUCKET_ID))

                    if(al.find { it.id == id } != null) {
                        continue
                    }

                    val albumProjection = arrayOf(MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA)
                    val image = context.contentResolver.query(imagesQueryUri, albumProjection, "${MediaStore.Images.Media.BUCKET_ID} = $id", null, "${MediaStore.Images.Media.DATE_ADDED} desc")
                    if (image != null) {
                        val quantity = image.count
                        image.moveToFirst()
                        val data = image.getString(image.getColumnIndex(MediaStore.Images.Media.DATA))
                        val name = image.getString(image.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))

                        if(id == albumId) break
                        albumId = id
                        al.add(
                            Album(
                                name,
                                id,
                                data,
                                quantity.toString()
                            )
                        )
                    }
                    image?.close()
                } while (albums.moveToNext())
            }
            albums?.close()
            al.toList()
        }
}
