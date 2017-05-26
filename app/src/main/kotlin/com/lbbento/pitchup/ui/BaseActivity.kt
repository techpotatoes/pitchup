package com.lbbento.pitchup.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.wearable.activity.WearableActivity
import com.lbbento.pitchup.MainApplication
import com.lbbento.pitchup.di.ControllerComponent
import com.lbbento.pitchup.di.ControllerModule

abstract class BaseActivity : WearableActivity(), BaseView {

    val controllerComponent: ControllerComponent
        get() {
            return (application as MainApplication).component.plus(ControllerModule(this))
        }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
    }

    abstract fun setupInjection()
}