package com.lbbento.pitchupwear.main

import android.os.Bundle
import android.widget.TextView
import com.lbbento.pitchupwear.R
import com.lbbento.pitchupwear.ui.BaseActivity


class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun setupInjection() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun updateTunerView(tunerViewModel: TunerViewModel) {
        val text = findViewById(R.id.text) as TextView
        text.text = tunerViewModel.note
    }
}
