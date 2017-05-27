package com.lbbento.pitchup.main

import android.os.Bundle
import com.lbbento.pitchup.R
import com.lbbento.pitchup.ui.BaseActivity

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun setupInjection() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()
    }
}
