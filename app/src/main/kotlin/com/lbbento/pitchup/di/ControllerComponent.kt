package com.lbbento.pitchup.di

import com.lbbento.pitchup.main.MainActivity
import dagger.Subcomponent

@ForController
@Subcomponent(modules = arrayOf(ControllerModule::class))
interface ControllerComponent {
    fun inject(mainActivity: MainActivity)
}