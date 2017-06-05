package com.lbbento.pitchupwear.main

import android.os.Bundle
import android.widget.TextView
import com.lbbento.pitchuptuner.service.TuningStatus
import com.lbbento.pitchupwear.R
import com.lbbento.pitchupwear.ui.BaseActivity
import com.lbbento.pitchupwear.ui.view.MySpeedView


class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun setupInjection() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSpeedometer()
    }

    override fun updateTunerView(tunerViewModel: TunerViewModel) {
        val text = findViewById(R.id.text) as TextView
        val speedometer = findViewById(R.id.speedometer) as MySpeedView

        if (tunerViewModel.tunningStatus != TuningStatus.DEFAULT) {
            if (text.text != tunerViewModel.note) {
                text.text = tunerViewModel.note
                speedometer.maxSpeed = (tunerViewModel.expectedFrequency + 3f).toInt() //TODO return it
                speedometer.minSpeed = (tunerViewModel.expectedFrequency - 3f).toInt()
            }
            speedometer.speedTo((tunerViewModel.expectedFrequency + tunerViewModel.diffFrequency).toFloat(), 350)
        } else if (text.text != "Play!")
            text.text = "Play!"

    }

    fun setupSpeedometer() {
        val speedometer: MySpeedView

        speedometer = findViewById(R.id.speedometer) as MySpeedView

        // configure value range and ticks
        speedometer.maxSpeed = 880
        speedometer.minSpeed = 0
        speedometer.setSpeedAt(440f)

        speedometer.isWithTremble = false
    }
}
