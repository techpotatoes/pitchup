package com.lbbento.pitchup

import android.app.Application
import com.lbbento.pitchup.di.AppComponent
import com.lbbento.pitchup.di.AppModule
import com.lbbento.pitchup.di.DaggerAppComponent

class MainApplication : Application() {

    val component: AppComponent
        get() = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()


    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}