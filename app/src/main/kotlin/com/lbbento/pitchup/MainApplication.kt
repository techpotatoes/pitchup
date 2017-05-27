package com.lbbento.pitchup

import android.app.Application
import com.lbbento.pitchup.di.AppComponent
import com.lbbento.pitchup.di.DaggerAppComponent

class MainApplication : Application() {

    val component: AppComponent
        get() = DaggerAppComponent.create()

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}