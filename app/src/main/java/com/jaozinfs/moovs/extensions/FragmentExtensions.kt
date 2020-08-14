package com.jaozinfs.moovs.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

infix fun Context?.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


inline fun <reified T> Fragment.argument(key: String, crossinline default: () -> T?): Lazy<T?> =
    lazy {
        val value = arguments?.get(key)
        if (value is T) value else default()
    }
