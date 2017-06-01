package com.lbbento.pitchupwear.main

import com.lbbento.pitchuptuner.service.TuningStatus

data class TunerViewModel(val note: String, val tunningStatus: TuningStatus, val expectedFrequency: Double, val diffFrequency: Double)