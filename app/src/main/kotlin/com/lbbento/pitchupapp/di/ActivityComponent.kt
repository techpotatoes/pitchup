package com.lbbento.pitchupapp.di

import com.lbbento.pitchupapp.main.MainActivity
import dagger.Subcomponent

@ForActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}