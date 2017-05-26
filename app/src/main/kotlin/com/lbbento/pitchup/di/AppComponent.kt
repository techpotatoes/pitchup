package com.lbbento.pitchup.di

import com.lbbento.pitchup.MainApplication
import dagger.Component

@ForApplication
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(mainApplication: MainApplication)
    fun plus(module: ControllerModule): ControllerComponent
}