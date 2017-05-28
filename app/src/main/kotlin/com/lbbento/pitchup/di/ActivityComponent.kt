package com.lbbento.pitchup.di

import com.lbbento.pitchup.main.MainActivity
import dagger.Subcomponent

@ForActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}