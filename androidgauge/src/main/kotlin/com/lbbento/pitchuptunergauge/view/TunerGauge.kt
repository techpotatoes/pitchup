package com.lbbento.pitchuptunergauge.view

import android.content.Context
import android.graphics.*
import android.graphics.Color.*
import android.util.AttributeSet
import com.github.anastr.speedviewlib.base.Speedometer
import com.github.anastr.speedviewlib.base.SpeedometerDefault

class TunerGauge(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : Speedometer(context, attrs, defStyleAttr) {

    companion object {
        val INDICATOR_COLOR = "#c6c6c6"
        val TUNED_COLOR = "#77C577"
    }

    private val ARC_COLOR = "#c6c6c6"

    private val markPath: Path
    private val circlePaint: Paint
    private val speedometerPaint: Paint
    private val markPaint: Paint
    private val middleMarkPaint: Paint
    private val speedometerRect: RectF

    init {
        this.markPath = Path()
        this.circlePaint = Paint(1)
        this.speedometerPaint = Paint(1)
        this.markPaint = Paint(1)
        this.middleMarkPaint = Paint(1)
        this.speedometerRect = RectF()
        this.init()
    }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun defaultValues() {}

    override fun getSpeedometerDefault(): SpeedometerDefault {
        val speedometerDefault = SpeedometerDefault()
        speedometerDefault.indicator = TunerIndicator(this.context)
        speedometerDefault.backgroundCircleColor = 0
        return speedometerDefault
    }

    private fun init() {
        this.speedometerPaint.style = Paint.Style.STROKE
        this.markPaint.style = Paint.Style.STROKE
        this.middleMarkPaint.style = Paint.Style.STROKE
        this.middleMarkPaint.color = parseColor(TUNED_COLOR)
        this.circlePaint.color = parseColor(INDICATOR_COLOR)
        this.startDegree = 180
        this.endDegree = 360
        this.lowSpeedPercent = 49
        this.mediumSpeedPercent = 51
        this.mediumSpeedColor = parseColor(ARC_COLOR)
        this.lowSpeedColor = parseColor(ARC_COLOR)
        this.highSpeedColor = parseColor(ARC_COLOR)
        this.indicatorColor = parseColor(INDICATOR_COLOR)
        this.markColor = parseColor(ARC_COLOR)
        this.speedTextColor = context.resources.getColor(android.R.color.transparent)
        this.speedTextTypeface = Typeface.SANS_SERIF
        this.speedometerWidth = 10f
        this.textColor = context.resources.getColor(android.R.color.transparent)
        this.unit = "Hz"
        this.unitTextColor = context.resources.getColor(android.R.color.transparent)
        this.backgroundCircleColor = context.resources.getColor(android.R.color.transparent)
    }

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        super.onSizeChanged(w, h, oldW, oldH)
        this.updateBackgroundBitmap()
    }

    private fun initDraw() {
        this.speedometerPaint.strokeWidth = this.speedometerWidth
        this.markPaint.color = this.markColor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.initDraw()
        this.drawSpeedUnitText(canvas)
        this.drawIndicator(canvas)
        canvas.drawCircle(this.size.toFloat() * 0.5f, this.size.toFloat() * 0.5f, this.widthPa.toFloat() / 20.0f, this.circlePaint)
        this.drawNotes(canvas)
    }

    override fun updateBackgroundBitmap() {
        val c = this.createBackgroundBitmapCanvas()
        this.initDraw()

        val markH = this.sizePa.toFloat() / 32.0f
        this.markPath.reset()
        this.markPath.moveTo((this.size.toFloat()) * 0.5f, this.padding.toFloat())
        this.markPath.lineTo((this.size.toFloat()) * 0.5f, markH + this.padding.toFloat())
        this.markPaint.strokeWidth = markH / 5.0f

        val risk = this.speedometerWidth * 0.5f + 30f //markers
        this.speedometerRect.set(risk, risk, this.size.toFloat() - risk, this.size.toFloat() - risk)

        //draw arc
        this.speedometerPaint.color = this.highSpeedColor
        c.drawArc(this.speedometerRect, this.startDegree.toFloat(), (this.endDegree - this.startDegree).toFloat(), false, this.speedometerPaint)
        this.speedometerPaint.color = this.mediumSpeedColor
        c.drawArc(this.speedometerRect, this.startDegree.toFloat(), (this.endDegree - this.startDegree).toFloat() * this.mediumSpeedOffset, false, this.speedometerPaint)
        this.speedometerPaint.color = this.lowSpeedColor
        c.drawArc(this.speedometerRect, this.startDegree.toFloat(), (this.endDegree - this.startDegree).toFloat() * this.lowSpeedOffset, false, this.speedometerPaint)

        c.save()
        c.rotate(90.0f + this.startDegree.toFloat(), this.size.toFloat() * 0.5f, this.size.toFloat() * 0.5f)

        c.rotate(10f, this.size.toFloat() * 0.5f, this.size.toFloat() * 0.5f)
        c.drawPath(this.markPath, this.markPaint)

        this.middleMarkPaint.strokeWidth = indicatorWidth + 10f
        c.rotate(80f, this.size.toFloat() * 0.5f, this.size.toFloat() * 0.5f)
        c.drawPath(this.markPath, this.middleMarkPaint) //Middle one

        c.rotate(80f, this.size.toFloat() * 0.5f, this.size.toFloat() * 0.5f)
        c.drawPath(this.markPath, this.markPaint)

        c.restore()
        this.drawDefMinMaxSpeedPosition(c)
    }

}