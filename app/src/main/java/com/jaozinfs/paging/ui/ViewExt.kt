package com.jaozinfs.paging.ui

import android.net.Uri
import android.view.View
import android.widget.ImageView
import coil.api.load
import coil.request.LoadRequestBuilder
import coil.size.Scale

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

fun ImageView.loadImageCoil(
    uri: Uri,
    scaleFii: Boolean?=null,
    onSuccess: () -> Unit = {}
) {
    load(uri) {
        scale(Scale.FIT)
        listener { request, source ->
            onSuccess.invoke()
        }
    }
}
