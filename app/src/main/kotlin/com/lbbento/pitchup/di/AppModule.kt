package com.lbbento.pitchup.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    @ForApplication
    fun provideApplicationContext(): Context = application
}