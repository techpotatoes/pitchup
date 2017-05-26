package com.lbbento.pitchup

import android.app.Application
import com.lbbento.daydreamnasa.di.AppComponent
import com.lbbento.daydreamnasa.di.AppModule
import com.lbbento.daydreamnasa.di.DaggerAppComponent

class MainApplication: Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)
    }

}