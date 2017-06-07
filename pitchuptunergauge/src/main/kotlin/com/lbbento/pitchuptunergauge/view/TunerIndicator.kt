package com.lbbento.pitchuptunergauge.view

import android.content.Context
import android.graphics.*
import com.github.anastr.speedviewlib.components.Indicators.Indicator
import com.github.anastr.speedviewlib.components.Indicators.LineIndicator

class TunerIndicator(context: Context) : Indicator<LineIndicator>(context) {
    private val indicatorPath = Path()
    val LINE = 1.0f
    private val mode: Float = LINE

    init {
        this.updateIndicator()
    }

    override fun getIndicatorColor(): Int {
        return super.getIndicatorColor()
    }

    override fun getDefaultIndicatorWidth(): Float {
        return this.dpTOpx(3f)
    }

    override fun draw(canvas: Canvas, degree: Float) {
        val maxAceptable = 270 + 3f
        val minAceptable = 270 - 3f

        if (degree in minAceptable..maxAceptable) {
            indicatorPaint.color = Color.GREEN
        } else {
            indicatorPaint.color = Color.parseColor("#ffff8800")
        }

        canvas.save()
        canvas.rotate(90.0f + degree, this.centerX, this.centerY)
        canvas.drawPath(this.indicatorPath, this.indicatorPaint)
        canvas.restore()
    }

    override fun updateIndicator() {
        this.indicatorPath.reset()
        this.indicatorPath.moveTo(this.centerX, this.padding.toFloat() + 26f)
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