package com.jaozinfs.paging.movies.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.jaozinfs.paging.movies.R

class FollowCustomView(private val ctx: Context, private val attributeSet: AttributeSet) :
    ConstraintLayout(ctx, attributeSet) {
    init {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.view_follow_button, this, true)
    }
}
