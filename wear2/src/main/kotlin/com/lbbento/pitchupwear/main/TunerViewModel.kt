package com.lbbento.pitchupwear.main

import com.lbbento.pitchupcore.TuningStatus

data class TunerViewModel(val note: String, val tuningStatus: TuningStatus, val expectedFrequency: Double, val diffFrequency: Double, val diffInCents: Double)