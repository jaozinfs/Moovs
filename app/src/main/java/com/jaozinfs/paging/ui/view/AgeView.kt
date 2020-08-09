package com.jaozinfs.paging.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi
import com.jaozinfs.paging.R
import kotlin.math.min

class AgeView(
    private val ctx: Context,
    private val attributeSet: AttributeSet
) : View(ctx, attributeSet) {
    private val Float.toSp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this, resources.displayMetrics
        )

    private val paint = Paint().apply {
        color = Color.RED
    }

    private val paintAge = Paint().apply {
        color = Color.WHITE
        textSize = 18F.toSp
        flags = Paint.ANTI_ALIAS_FLAG

    }

    private var age = "18"

    /**
     * Value referent the corner of view
     */
    private var ageCorner: Float

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.AgeView).apply {
            if (hasValue(R.styleable.AgeView_age))
                setAgeDetails(getString(R.styleable.AgeView_age))

            ageCorner = getFloat(R.styleable.AgeView_ageview_conrner_radius, 8f).dp

        }.recycle()
    }

    /**
     * Seta a cor a partir da idade
     */
    fun setAgeDetails(age: String?) {
        paint.color = getColorByAge(age)
        this@AgeView.age = age ?: throw Exception("Age is null")
        invalidate()
    }

    fun setIsAdult(adult: Boolean) {
        val age = if (adult) "18" else "L"
        paint.color = getColorByAge(age)
        this@AgeView.age = age
        invalidate()
    }


    val Float.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            resources.displayMetrics
        )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val cX = (width / 2).toFloat()
        val cY = (height / 2).toFloat()
        val radius = (min(cX, cY) * 1f) - 10f

        canvas?.drawRoundRect(
            cX - radius, cY - radius, cX + radius, cY + radius,
            ageCorner,
            ageCorner,
            paint
        )
        val bounds = Rect()
        paintAge.getTextBounds(age, 0, age.length, bounds)
        canvas?.drawText(age, (cX - (bounds.width() / 2) - 5), cY + (bounds.height() / 2), paintAge)
    }

    private fun getColorByAge(age: String?): Int {
        return when (age) {
            "L" -> {
                Color.GREEN
            }
            "10" -> {
                Color.BLUE
            }
            "12" -> {
                Color.YELLOW
            }
            "14" -> {
                Color.rgb(255, 165, 0)
            }
            "16" -> {
                Color.RED
            }
            "18" -> {
                Color.BLACK
            }
            else -> {
                throw NotImplementedError("Esta idade não foi implmentada")
            }
        }
    }
}