package com.jaozinfs.moovs.extensions

import androidx.constraintlayout.motion.widget.MotionLayout
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun MotionLayout.transitionToEndAndAwait() = suspendCoroutine<Unit> {
    val listener = object : MotionLayout.TransitionListener {
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, progfress: Float) {}
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, progfress: Float) {}
        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            it.resume(Unit)
        }
    }
    addTransitionListener(listener)
    transitionToEnd()
}