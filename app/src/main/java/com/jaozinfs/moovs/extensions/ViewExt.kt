package com.jaozinfs.moovs.extensions

import android.net.Uri
import android.view.View
import android.widget.ImageView
import coil.api.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation

fun View.setClickListener(clickListener: () -> Unit) =
    setOnClickListener { clickListener.invoke() }

fun <T : View> View.lazyFindView(id: Int): Lazy<T> =
    lazy {
        findViewById<T>(id)
    }


fun ImageView.loadImageCoil(
    uri: Uri,
    corners: Boolean = false,
    onSuccess: () -> Unit = {}
) {
    load(uri) {
        scale(Scale.FIT)
        if (corners)
            transformations(RoundedCornersTransformation(6f))
        listener { _, _ ->
            onSuccess.invoke()
        }
    }
}

class CoilLoadBuilder(
    var uri: Uri? = null,
    var corners: Boolean = false,
    var cornersRadius: Float = 6f,
    var onSuccess: () -> Unit = {}
)

fun ImageView.loadImageCoil(
    scope: CoilLoadBuilder.() -> Unit
) {
    val builder = CoilLoadBuilder().apply(scope)
    with(builder) {
        load(uri) {
            scale(Scale.FIT)
            if (corners)
                transformations(RoundedCornersTransformation(cornersRadius))
            listener { _, _ ->
                onSuccess.invoke()
            }
        }
    }

}
