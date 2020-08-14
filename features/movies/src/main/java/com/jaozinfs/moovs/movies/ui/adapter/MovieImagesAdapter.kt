package com.jaozinfs.moovs.movies.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jaozinfs.moovs.movies.R
import com.jaozinfs.moovs.movies.data.network.BASE_BACKDROP_IMAGE_PATTER
import com.jaozinfs.moovs.extensions.lazyFindView
import com.jaozinfs.moovs.extensions.loadImageCoil

class MovieImagesAdapter : RecyclerView.Adapter<MovieImagesAdapter.PageHolder>() {
    private var listBackgroundsImages = emptyList<String>()
    fun getAdapterSize() = listBackgroundsImages.size
    private var clickListener: (() -> Unit)? = null

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

    inner class PageHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val image_bg by root.lazyFindView<ImageView>(R.id.image_bg)

        internal fun bindItem(filePath: String) {
            //set listener
            root.setOnClickListener {
                clickListener?.invoke()
            }

            val uriBackground = Uri.parse(BASE_BACKDROP_IMAGE_PATTER)
                .buildUpon()
                .appendEncodedPath(filePath)
                .build()
            image_bg.loadImageCoil(uriBackground)
        }
    }

    fun setList(listImages: List<String>) {
        this@MovieImagesAdapter.listBackgroundsImages = listImages
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(function: () -> Unit) {
        clickListener = function
    }


}