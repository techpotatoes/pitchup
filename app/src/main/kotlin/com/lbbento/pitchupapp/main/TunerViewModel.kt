package com.lbbento.pitchupapp.main

import com.lbbento.pitchupcore.TuningStatus

data class TunerViewModel(val note: String, val tunningStatus: TuningStatus, val expectedFrequency: Double, val diffFrequency: Double, val diffInCents: Double)