package com.lbbento.pitchupapp.main

import com.lbbento.pitchupapp.ui.BaseView

interface MainView : BaseView {
    fun setupGauge()
    fun updateNote(note: String)
    fun updateToDefaultStatus()
    fun updateIndicator(diffInCents: Float)
    fun informError()
    fun updateCurrentFrequency(currentFreq: Float)
    fun updateCurrentDifferenceInCents(cents: Float)
}