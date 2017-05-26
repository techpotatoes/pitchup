package com.lbbento.pitchup.main

import android.os.Bundle
import com.lbbento.pitchup.MainApplication
import com.lbbento.pitchup.R
import com.lbbento.pitchup.ui.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    override fun setupInjection() {
        (application as MainApplication).component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onAttachedToWindow(this)

        // Enables Always-on
        setAmbientEnabled()
    }
}
