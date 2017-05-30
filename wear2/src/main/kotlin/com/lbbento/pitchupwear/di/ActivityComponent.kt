package com.lbbento.pitchupwear.di

import com.lbbento.pitchupwear.main.MainActivity
import dagger.Subcomponent

@ForActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}