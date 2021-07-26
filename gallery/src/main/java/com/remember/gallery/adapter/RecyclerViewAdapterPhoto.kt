package com.remember.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.remember.common.widget.loadUri
import com.remember.gallery.R
import com.remember.gallery.model.Image

class RecyclerViewAdapterPhoto(
    private val photos: List<Image>,
    private val size: Int,
    private val onClickImage: (Image) -> Unit
) : RecyclerView.Adapter<ViewHolderPhoto>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPhoto {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_photo, parent, false)
        view.layoutParams?.let {
            it.height = size
            it.width = size
        }
        view.requestLayout()
        return ViewHolderPhoto(view)
    }

    override fun getItemCount() = photos.count()

    override fun onBindViewHolder(holder: ViewHolderPhoto, position: Int) {
        holder.bind(photos[position], onClickImage)
    }
}

class ViewHolderPhoto(val view: View) : RecyclerView.ViewHolder(view) {
    private val image: AppCompatImageView = itemView.findViewById(R.id.image)

    fun bind(photo: Image, onClickImage: (Image) -> Unit) {
        image.clipToOutline = true
        image.loadUri(photo.data, crossFadeDuration = 500)
        image.setOnClickListener { onClickImage.invoke(photo) }
    }
}
