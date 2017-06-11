package com.lbbento.pitchupapp

import android.app.Application
import com.lbbento.pitchupapp.di.AppComponent
import com.lbbento.pitchupapp.di.DaggerAppComponent

class MainApplication : Application() {

    val component: AppComponent
        get() = DaggerAppComponent.builder().build()

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}