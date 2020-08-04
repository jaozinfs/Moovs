package com.jaozinfs.paging.movies.ui.animation

import androidx.constraintlayout.motion.widget.MotionLayout

class MovieFavoriteTransition(private val transitionComplete: (Int) -> Unit) :
    MotionLayout.TransitionListener {
    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        transitionComplete.invoke(p1)
    }
}