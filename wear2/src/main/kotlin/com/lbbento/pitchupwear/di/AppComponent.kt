package com.lbbento.pitchupwear.di

import com.lbbento.pitchupwear.MainApplication
import dagger.Component

@ForApplication
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(mainApplication: MainApplication)
    fun plus(module: ActivityModule): ActivityComponent
}