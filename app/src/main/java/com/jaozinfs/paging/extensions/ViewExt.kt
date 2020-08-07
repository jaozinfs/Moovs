package com.jaozinfs.paging.extensions

import android.net.Uri
import android.view.View
import android.widget.ImageView
import coil.api.load
import coil.size.Scale

fun View.setClickListener(clickListener: () -> Unit) =
    setOnClickListener { clickListener.invoke() }

fun <T : View> View.lazyFindView(id: Int): Lazy<T> =
    lazy {
        findViewById<T>(id)
    }


fun ImageView.loadImageCoil(
    uri: Uri,
    onSuccess: () -> Unit = {}
) {
    load(uri) {
        scale(Scale.FIT)
        listener { _, _ ->
            onSuccess.invoke()
        }
    }
}
