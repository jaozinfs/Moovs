package com.jaozinfs.moovs.extensions

import android.gesture.GestureOverlayView
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.jaozinfs.moovs.R

fun ViewPager2.transformCarroucel() {
    val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.defaultPagerMargin)
    val offsetPx = resources.getDimensionPixelOffset(R.dimen.defaultPagerOffset)
    setPageTransformer { page, position ->
        val viewPager = page.parent.parent as ViewPager2
        val offset = position * -(2 * offsetPx + pageMarginPx)
        if (viewPager.orientation == GestureOverlayView.ORIENTATION_HORIZONTAL) {
            if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.translationX = -offset
            } else {
                page.translationX = offset
            }
        } else {
            page.translationY = offset
        }
    }
}
