package com.lbbento.daydreamnasa.di

import com.lbbento.pitchup.MainApplication
import com.lbbento.pitchup.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(mainApplication : MainApplication)
    fun inject(mainActivity: MainActivity)
}