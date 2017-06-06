package com.lbbento.pitchupwear.main

import android.os.Bundle
import android.widget.TextView
import com.lbbento.pitchuptuner.service.TuningStatus
import com.lbbento.pitchuptunergauge.view.TunerGauge
import com.lbbento.pitchupwear.R
import com.lbbento.pitchupwear.ui.BaseActivity


class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun setupInjection() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupGauge()
    }

    override fun updateTunerView(tunerViewModel: TunerViewModel) {
        val text = findViewById(R.id.text) as TextView
        val gauge = findViewById(R.id.gauge) as TunerGauge

        if (tunerViewModel.tunningStatus != TuningStatus.DEFAULT) {
            if (text.text != tunerViewModel.note) {
                text.text = tunerViewModel.note
                gauge.maxSpeed = (tunerViewModel.expectedFrequency + 3f).toInt() //TODO return it
                gauge.minSpeed = (tunerViewModel.expectedFrequency - 3f).toInt()
            }
            gauge.speedTo((tunerViewModel.expectedFrequency + tunerViewModel.diffFrequency).toFloat(), 350)
        } else if (text.text != "Play!")
            text.text = "Play!"

    }

    fun setupGauge() {
        val gauge: TunerGauge

        gauge = findViewById(R.id.gauge) as TunerGauge

        // configure value range and ticks
        gauge.maxSpeed = 880
        gauge.minSpeed = 0
        gauge.setSpeedAt(440f)

        gauge.isWithTremble = false
    }
}
