package com.lbbento.pitchup.di

import com.lbbento.pitchup.main.MainActivity
import dagger.Subcomponent

@ForController
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}