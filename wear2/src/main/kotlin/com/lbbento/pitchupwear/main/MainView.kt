package com.lbbento.pitchupwear.main

import com.lbbento.pitchupwear.ui.BaseView

interface MainView : BaseView {
    fun setupGauge()
    fun updateNote(note: String)
    fun updateToDefaultStatus()
    fun updateIndicator(diffInCents: Float)
    fun getCurrentNote(): String
    fun informError()
    fun updateCurrentFrequency(currentFreq: Float)
}