package com.example.paging3.ui

import android.view.View

fun View.setClickListener(clickListener: () -> Unit) =
    setOnClickListener { clickListener.invoke() }