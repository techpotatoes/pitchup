package com.lbbento.pitchup.di

import android.app.Application
import android.content.Context
import com.lbbento.pitchup.service.TunerService
import com.lbbento.pitchup.service.TunerServiceImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplicationContext(): Context = application

    @Provides
    fun provideTunerService(): TunerService = TunerServiceImpl()
}