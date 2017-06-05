package com.lbbento.pitchupwear.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.github.anastr.speedviewlib.R
import com.github.anastr.speedviewlib.base.Speedometer
import com.github.anastr.speedviewlib.base.SpeedometerDefault
import com.github.anastr.speedviewlib.components.Indicators.Indicator
import com.github.anastr.speedviewlib.components.Indicators.LineIndicator

class MySpeedView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : Speedometer(context, attrs, defStyleAttr) {

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
        this.initAttributeSet(context, attrs)
    }

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun defaultValues() {}

    override fun getSpeedometerDefault(): SpeedometerDefault {
        val speedometerDefault = SpeedometerDefault()
        speedometerDefault.indicator = MyIndicator(this.context)
        speedometerDefault.backgroundCircleColor = 0
        return speedometerDefault
    }

    private fun init() {
        this.speedometerPaint.style = Paint.Style.STROKE
        this.markPaint.style = Paint.Style.STROKE
        this.middleMarkPaint.style = Paint.Style.STROKE
        this.middleMarkPaint.color = Color.GREEN
        this.circlePaint.color = -12303292
    }

    private fun initAttributeSet(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(attrs, R.styleable.SpeedView, 0, 0)
            this.circlePaint.color = a.getColor(R.styleable.SpeedView_sv_centerCircleColor, this.circlePaint.color)
            a.recycle()
        }
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
//        val everyDegree = (this.endDegree - this.startDegree).toFloat() * 0.111f
//
//        var i = this.startDegree.toFloat()
//        while (i < this.endDegree.toFloat() - 2.0f * everyDegree) {
//            c.rotate(everyDegree, this.size.toFloat() * 0.5f, this.size.toFloat() * 0.5f)
//            c.drawPath(this.markPath, this.markPaint)
//            i += everyDegree
//        }


        c.rotate(10f, this.size.toFloat() * 0.5f, this.size.toFloat() * 0.5f)
        c.drawPath(this.markPath, this.markPaint)

        this.middleMarkPaint.strokeWidth = indicatorWidth + 5f
        c.rotate(80f, this.size.toFloat() * 0.5f, this.size.toFloat() * 0.5f)
        c.drawPath(this.markPath, this.middleMarkPaint) //Middle one

        c.rotate(78f, this.size.toFloat() * 0.5f, this.size.toFloat() * 0.5f)
        c.drawPath(this.markPath, this.markPaint)

        c.restore()
        this.drawDefMinMaxSpeedPosition(c)
    }

    class MyIndicator(context: Context) : Indicator<LineIndicator>(context) {
        private val indicatorPath = Path()
        val LINE = 1.0f
        private val mode: Float = LINE

        init {
            this.updateIndicator()
        }

        override fun getDefaultIndicatorWidth(): Float {
            return this.dpTOpx(3f)
        }

        override fun draw(canvas: Canvas, degree: Float) {
            canvas.save()
            canvas.rotate(90.0f + degree, this.centerX, this.centerY)
            canvas.drawPath(this.indicatorPath, this.indicatorPaint)
            canvas.restore()
        }

        override fun updateIndicator() {
            this.indicatorPath.reset()
            this.indicatorPath.moveTo(this.centerX, this.padding.toFloat() + 26f) //line
            this.indicatorPath.lineTo(this.centerX, this.centerY * this.mode)
            this.indicatorPaint.style = Paint.Style.STROKE
            this.indicatorPaint.strokeWidth = this.indicatorWidth
            this.indicatorPaint.color = this.indicatorColor
        }

        override fun setWithEffects(withEffects: Boolean) {
            if (withEffects && !this.isInEditMode) {
                this.indicatorPaint.maskFilter = BlurMaskFilter(15.0f, BlurMaskFilter.Blur.SOLID)
            } else {
                this.indicatorPaint.maskFilter = null as MaskFilter?
            }

        }
    }
}