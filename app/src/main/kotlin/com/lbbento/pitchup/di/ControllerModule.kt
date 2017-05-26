package com.lbbento.pitchup.di

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ControllerModule(val activity: Activity) {

    @Provides
    fun context(): Context {
        return activity
    }

    @Provides
    fun activityBase(): Activity {
        return activity
    }
}