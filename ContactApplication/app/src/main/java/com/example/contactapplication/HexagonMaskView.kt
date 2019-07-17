package com.example.contactapplication


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView


class HexagonMaskView : android.support.v7.widget.AppCompatImageView {
    private var hexagonPath: Path? = null
    private var hexagonBorderPath: Path? = null
    private var mBorderPaint: Paint? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        this.hexagonPath = Path()
        this.hexagonBorderPath = Path()

        this.mBorderPaint = Paint()
        this.mBorderPaint!!.color = Color.WHITE
        this.mBorderPaint!!.strokeCap = Paint.Cap.ROUND
        this.mBorderPaint!!.strokeWidth = 1f
        this.mBorderPaint!!.style = Paint.Style.STROKE
    }

    fun setRadius(radius: Float) {
        calculatePath(radius)
    }

    fun setBorderColor(color: Int) {
        this.mBorderPaint!!.color = color
        invalidate()
    }

    private fun calculatePath(radius: Float) {
        val halfRadius = radius / 2f
        val triangleHeight = (Math.sqrt(3.0) * halfRadius).toFloat()
        val centerX = measuredWidth / 2f
        val centerY = measuredHeight / 2f

        this.hexagonPath!!.reset()
        this.hexagonPath!!.moveTo(centerX, centerY + radius)
        this.hexagonPath!!.lineTo(centerX - triangleHeight, centerY + halfRadius)
        this.hexagonPath!!.lineTo(centerX - triangleHeight, centerY - halfRadius)
        this.hexagonPath!!.lineTo(centerX, centerY - radius)
        this.hexagonPath!!.lineTo(centerX + triangleHeight, centerY - halfRadius)
        this.hexagonPath!!.lineTo(centerX + triangleHeight, centerY + halfRadius)
        this.hexagonPath!!.close()

        val radiusBorder = radius - 5f
        val halfRadiusBorder = radiusBorder / 2f
        val triangleBorderHeight = (Math.sqrt(3.0) * halfRadiusBorder).toFloat()

        this.hexagonBorderPath!!.reset()
        this.hexagonBorderPath!!.moveTo(centerX, centerY + radiusBorder)
        this.hexagonBorderPath!!.lineTo(centerX - triangleBorderHeight, centerY + halfRadiusBorder)
        this.hexagonBorderPath!!.lineTo(centerX - triangleBorderHeight, centerY - halfRadiusBorder)
        this.hexagonBorderPath!!.lineTo(centerX, centerY - radiusBorder)
        this.hexagonBorderPath!!.lineTo(centerX + triangleBorderHeight, centerY - halfRadiusBorder)
        this.hexagonBorderPath!!.lineTo(centerX + triangleBorderHeight, centerY + halfRadiusBorder)
        this.hexagonBorderPath!!.close()
        invalidate()
    }

    public override fun onDraw(c: Canvas) {
        c.drawPath(hexagonBorderPath!!, mBorderPaint!!)
        c.clipPath(hexagonPath!!, Region.Op.INTERSECT)
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        super.onDraw(c)
    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
        calculatePath(Math.min(width / 2f, height / 2f) - 10f)
    }
}