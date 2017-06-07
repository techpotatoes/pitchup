package com.lbbento.pitchupwear.main

import com.lbbento.pitchupwear.ui.BaseView

interface MainView : BaseView {
    fun setupGauge()
    fun updateToNote(note: String)
    fun updateToDefaultStatus()
    fun updateFrequency(freq: Float)
    fun updateMaxMinFreq(minFreq: Int, maxFreq: Int)
    fun getCurrentNote(): String
    fun informError()
}