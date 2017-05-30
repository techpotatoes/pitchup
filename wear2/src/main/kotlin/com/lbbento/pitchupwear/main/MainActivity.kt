package com.lbbento.pitchupwear.main

import android.os.Bundle
import com.lbbento.pitchupwear.R
import com.lbbento.pitchupwear.ui.BaseActivity

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun setupInjection() {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO - just enable when listening to a valid frequency
        // Enables Always-on
        setAmbientEnabled()
    }
}
