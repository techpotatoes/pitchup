package com.lbbento.pitchup.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.wearable.activity.WearableActivity

abstract class BaseActivity : WearableActivity(), BaseView {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
    }

    abstract fun setupInjection()
}