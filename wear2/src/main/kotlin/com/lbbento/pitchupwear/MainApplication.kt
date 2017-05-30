package com.lbbento.pitchupwear

import android.app.Application
import com.lbbento.pitchupwear.di.AppComponent
import com.lbbento.pitchupwear.di.DaggerAppComponent

class MainApplication : Application() {

    val component: AppComponent
        get() = DaggerAppComponent.builder().build()

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}