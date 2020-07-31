package com.jaozinfs.paging.ui

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

fun View.setClickListener(clickListener: () -> Unit) =
    setOnClickListener { clickListener.invoke() }


inline fun <T : View> View.lazyFindView(id: Int): Lazy<T> =
    lazy {
        findViewById<T>(id)
    }

sealed class Ajustments {
    object CenterCrop : Ajustments()
    object FitXY : Ajustments()
}

//Load image url and set in imageview with Picasso
fun ImageView.loadImageUrl(
    url: Uri,
    ajustments: Ajustments = Ajustments.CenterCrop,
    animate: Boolean = false,
    onLoadingFinished: () -> Unit = {}
) {
    (context as AppCompatActivity?)?.let {
        val listener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished.invoke()
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: com.bumptech.glide.request.target.Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished.invoke()
                return false
            }
        }
        val builder = Glide.with(context)
            .load(url)
            .listener(listener)
        builder.takeUnless { animate }?.dontAnimate()

        //set ajustments
        when (ajustments) {
            Ajustments.CenterCrop -> builder.centerCrop()
            Ajustments.FitXY -> builder.centerInside()
        }
        builder.into(this)
    }

}