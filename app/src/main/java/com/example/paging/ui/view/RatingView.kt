package com.example.paging.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.example.paging.R
import kotlin.math.min
import kotlin.math.round


class RatingView(
    private val ctx: Context,
    private val attrs: AttributeSet
) : View(ctx, attrs) {
    private val progressPaint: Paint by lazy {
        Paint().apply {
            color = Color.BLACK
            flags = Paint.ANTI_ALIAS_FLAG
        }
    }
    private val paintCircle = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
    }
    private val paintRating = Paint().apply {
        color = Color.WHITE
        textSize = 17F.toSp
        flags = Paint.ANTI_ALIAS_FLAG

    }
    private val Float.toSp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this, resources.displayMetrics
        )
    private var rating = "0.0"
    private var percent = 0F

    init {


        context.obtainStyledAttributes(attrs, R.styleable.RatingView).apply {
            if (hasValue(R.styleable.RatingView_primaryColor))
               paintCircle.color =  getColor(R.styleable.RatingView_primaryColor, Color.WHITE)

            if (hasValue(R.styleable.RatingView_progressColor))
                paintRating.color = getColor(R.styleable.RatingView_progressColor, Color.WHITE)

        }.recycle()


    }

    fun setPercent(p: Float, anim: Boolean = true) {
        ValueAnimator.ofFloat(0f, p).takeIf { anim }?.apply {
            duration = 1000
            addUpdateListener {
                setPercent(it.animatedValue as Float)
            }
        }?.start() ?: setPercent(p)
    }

    fun setPercent(p: Double?, anim: Boolean = true) {
        p ?: return
        ValueAnimator.ofFloat(0f, p.toFloat()).takeIf { anim }?.apply {
            duration = 1000
            addUpdateListener {
                setPercent(it.animatedValue as Float)
            }
        }?.start() ?: setPercent(p.toFloat())
    }

    private fun setPercent(p: Float) {
        percent = p
        rating = p.toDouble().round(1).toString()
        Log.d("Teste", "Percent $p")
        when (p) {
            in 0.toDouble()..3.toDouble() ->
                progressPaint.color = Color.GRAY
            in 4.toDouble()..6.toDouble() ->
                progressPaint.color = Color.YELLOW
            in 7.toDouble()..10.toDouble() ->
                progressPaint.color = Color.CYAN
        }
        invalidate()
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    override fun onDraw(canvas: Canvas?) {
        val cX: Float = (width / 2).toFloat()
        val cY = (height / 2).toFloat()
        val radius = min(cX, cY) * 1f

        canvas?.apply {
            super.onDraw(canvas)

            drawCircle(cX, cY, radius, paintCircle)

            drawArc(
                RectF(cX - radius, cY - radius, cX + radius, cY + radius),
                (-90).toFloat(),
                ((percent * 360) / 10).toFloat(),
                true,
                progressPaint
            )

            drawCircle(cX, cY, radius - 10, paintCircle)
            val bounds = Rect()
            val p = paintRating.getTextBounds(rating, 0, rating.length, bounds)
            drawText(rating, (cX - (bounds.width() / 2)), cY + (bounds.height() / 2), paintRating)

        }

    }
}