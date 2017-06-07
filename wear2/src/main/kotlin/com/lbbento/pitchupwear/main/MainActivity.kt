package com.lbbento.pitchupwear.main

import android.os.Bundle
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.lbbento.pitchupwear.R
import com.lbbento.pitchupwear.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun setupInjection() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun updateToNote(note: String) {
        main_activity_notetext.text = note
    }

    override fun updateMaxMinFreq(minFreq: Int, maxFreq: Int) {
        main_activity_gauge.minSpeed = minFreq
        main_activity_gauge.maxSpeed = maxFreq
    }

    override fun updateToDefaultStatus() {
        main_activity_notetext.text = getString(R.string.main_activity_play)
    }

    override fun updateFrequency(freq: Float) {
        main_activity_gauge.speedTo(freq, 350)
    }

    override fun getCurrentNote(): String {
        return main_activity_notetext.text.toString()
    }

    override fun informError() {
        makeText(this, R.string.error_occurred, LENGTH_SHORT).show()
    }

    override fun setupGauge() {
        main_activity_gauge.maxSpeed = 880
        main_activity_gauge.minSpeed = 0
        main_activity_gauge.isWithTremble = true
        main_activity_gauge.speedTo(440f, 1000)
    }
}
