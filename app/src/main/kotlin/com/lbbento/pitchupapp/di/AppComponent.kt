package com.lbbento.pitchupapp.di

import com.lbbento.pitchupapp.MainApplication
import dagger.Component

@ForApplication
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(mainApplication: MainApplication)
    fun plus(module: ActivityModule): ActivityComponent
}