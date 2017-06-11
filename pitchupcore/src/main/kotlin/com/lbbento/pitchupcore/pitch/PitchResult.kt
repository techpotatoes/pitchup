package com.lbbento.pitchupcore.pitch

import com.lbbento.pitchupcore.TuningStatus

data class PitchResult(val note: String, val tuningStatus: TuningStatus, val expectedFrequency: Double, val diffFrequency: Double, val diffCents: Double)