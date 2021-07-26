package com.remember.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.remember.common.widget.loadUri
import com.remember.extension.gone
import com.remember.gallery.R
import com.remember.repository.model.Album

class RecyclerViewAdapterAlbums(
    private val albums: List<Album>,
    private val size: Int,
    private val onClick: (Album) -> Unit
) : RecyclerView.Adapter<ViewHolderAlbums>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAlbums {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_album, parent, false)
        view.layoutParams?.apply {
            width = size
            height = size
        }
        view.requestLayout()
        return ViewHolderAlbums(view)
    }

    override fun getItemCount() = albums.count()

    override fun onBindViewHolder(holder: ViewHolderAlbums, position: Int) {
        holder.bind(albums[position], onClick, position % 2 == 0)
    }
}

class ViewHolderAlbums(val view: View) : RecyclerView.ViewHolder(view) {
    private val name: AppCompatTextView = itemView.findViewById(R.id.album_name)
    private val quantityImages: AppCompatTextView = itemView.findViewById(R.id.album_quantity_images)
    private val firstImage: AppCompatImageView = itemView.findViewById(R.id.first_image)

    fun bind(album: Album, onClick: (Album) -> Unit, isEven: Boolean) {

        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.let {
            if (isEven)
                it.setMargins(view.marginLeft, view.marginTop, 2.px(view.context), view.marginBottom)
            else
                it.setMargins(2.px(view.context), view.marginTop, view.marginRight, view.marginBottom)
        }
        view.requestLayout()

        album.name?.let {
            name.text = if(it.length >= 10) "${it.take(10)}..." else it
        } ?: name.gone()
        quantityImages.text = album.images
        firstImage.clipToOutline = true
        firstImage.scaleType = ImageView.ScaleType.CENTER_CROP
        firstImage.loadUri(album.uriFirstImage)
        firstImage.setOnClickListener {
            onClick.invoke(album)
        }
    }
}

fun Int.px(context: Context) = (this * context.resources.displayMetrics.density).toInt()
