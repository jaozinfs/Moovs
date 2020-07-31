package com.jaozinfs.paging.movies.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jaozinfs.paging.movies.R
import com.jaozinfs.paging.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.paging.ui.lazyFindView
import com.jaozinfs.paging.ui.loadImageUrl

class MovieImagesAdapter : RecyclerView.Adapter<MovieImagesAdapter.PageHolder>() {
    private var listBackgroundsImages = emptyList<String>()
    fun getAdapterSize() = listBackgroundsImages.size
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PageHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_images_item, parent, false)
        return PageHolder(view)
    }

    override fun getItemCount(): Int = listBackgroundsImages.size

    override fun onBindViewHolder(
        holder: PageHolder,
        position: Int
    ) {
        holder.bindItem(listBackgroundsImages[position])
    }

    inner class PageHolder(root: View) : RecyclerView.ViewHolder(root) {
        val image_bg by root.lazyFindView<ImageView>(R.id.image_bg)

        internal fun bindItem(filePath: String) {
            val uriBackground = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
                .buildUpon()
                .appendEncodedPath(filePath)
                .build()
            image_bg.loadImageUrl(uriBackground, animate = true)
        }
    }

    fun setList(listImages: List<String>) {
        this@MovieImagesAdapter.listBackgroundsImages = listImages
        notifyDataSetChanged()
    }


}