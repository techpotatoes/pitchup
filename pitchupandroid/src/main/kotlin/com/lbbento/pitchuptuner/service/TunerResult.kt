package com.lbbento.pitchuptuner.service

import com.lbbento.pitchupcore.TuningStatus

data class TunerResult(val note: String, val tunningStatus: TuningStatus, val expectedFrequency: Double, val diffFrequency: Double, val diffCents: Double)