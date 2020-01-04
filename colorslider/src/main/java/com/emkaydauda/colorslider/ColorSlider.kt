package com.emkaydauda.colorslider


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.SeekBar

class ColorSlider @JvmOverloads
constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleAttr: Int = R.attr.seekBarStyle,
    styleRes: Int = 0
) : SeekBar(context, attributeSet, styleAttr, styleRes) {

    private var colors: ArrayList<Int> = arrayListOf(Color.RED, Color.GREEN, Color.DKGRAY)

    private val w = 16f.toDip(context)
    private val h = 16f.toDip(context)

    private val halfW = if (w >= 0) w / 2f else 1f
    private val halfH = if (h >= 0) h / 2f else 1f

    private val paint = Paint()

    private var noColorDrawable: Drawable? = null
        set(value) {
            w2 = value?.intrinsicWidth ?: 0
            h2 = value?.intrinsicHeight ?: 0
            halfW2 = if (w2 >= 0) w2 / 2 else 1
            halfH2 = if (h2 >= 0) h2 / 2 else 1

            value?.setBounds(-halfW2, -halfH2, halfW2, halfH2)
            field = value
        }

    var w2 = 0
    var h2 = 0
    var halfW2 = 1
    var halfH2 = 1

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ColorSlider)
        try {
            colors = typedArray.getTextArray(R.styleable.ColorSlider_colors).map {
                Color.parseColor(it.toString())
            } as ArrayList<Int>
        } finally {
            typedArray.recycle()
        }

        noColorDrawable = context.getDrawable(R.drawable.ic_clear_red_24dp)


        colors.add(0, android.R.color.transparent)
        max = colors.size - 1
        progressTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        progressBackgroundTintList = ContextCompat.getColorStateList(context, android.R.color.transparent)
        splitTrack = false

        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom + 16f.toDip(context).toInt())

        thumb = context.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp)

        setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, position: Int, p2: Boolean) {
                listeners.forEach {
                    it(colors[position])
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawTickMarks(canvas)
    }

    private var listeners: ArrayList<(Int) -> Unit> = arrayListOf()

    var selectedColorValue: Int = android.R.color.transparent
        set(value) {
            var index = colors.indexOf(value)
            progress = if (index == -1) 0 else index
        }

    fun addListener(function: (Int) -> Unit) {
        listeners.add(function)
    }

    private fun drawTickMarks(canvas: Canvas?) {
        canvas?.let {
            val count = colors.size
            val saveCount = canvas.save()
            canvas.translate(paddingLeft.toFloat(), (height / 2f) + 8f.toDip(context))
            if (count > 1) {

                for (i in 0 until count) {
                    val spacing = (width - paddingLeft - paddingRight) / (count - 1).toFloat()

                    if (i == 0) {
                        noColorDrawable?.draw(canvas)
                    } else {
                        paint.color = colors[i]
                        canvas.drawRoundRect(-halfW, -halfH, halfW, halfH, 2f.toDip(context), 2f.toDip(context), paint)
                    }
                    canvas.translate(spacing, 0f)
                }
                canvas.restoreToCount(saveCount)
            }
        }
    }
}

private fun Float.toDip(context: Context): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)
}
