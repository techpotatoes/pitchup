package com.lbbento.pitchupapp.main

import android.os.Bundle
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import com.lbbento.pitchupapp.R
import com.lbbento.pitchupapp.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun setupInjection() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun updateNote(note: String) {
        main_activity_note_view.smoothScrollToNote(note)
    }

    override fun updateToDefaultStatus() {
        //TODO - ?
    }

    override fun updateIndicator(diffInCents: Float) {
        main_activity_gauge.speedTo(diffInCents, 600)
    }

    override fun updateCurrentFrequency(currentFreq: Float) {
        main_activity_freqtext.text = getString(R.string.freq_in_hertz, currentFreq)
    }

    override fun updateCurrentDifferenceInCents(cents: Float) {
        main_activity_centstext.text = getString(R.string.number_in_cents, cents)
    }

    override fun informError() {
        makeText(this, R.string.error_occurred, LENGTH_SHORT).show()
    }

    override fun setupGauge() {
        main_activity_gauge.maxSpeed = 100
        main_activity_gauge.minSpeed = -100
        main_activity_gauge.speedTo(0f, 1000)
    }
}
