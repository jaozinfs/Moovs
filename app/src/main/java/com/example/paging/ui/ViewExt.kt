package com.example.paging.ui

import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun View.setClickListener(clickListener: () -> Unit) =
    setOnClickListener { clickListener.invoke() }


inline fun <T : View> View.lazyFindView(id: Int): Lazy<T> =
    lazy {
        findViewById<T>(id)
    }

//Load image url and set in imageview with Picasso
infix fun ImageView.loadImageUrl(url: Uri) {
    Picasso.get()
        .load(url)
        .fit()
        .centerCrop()
        .into(this)
}