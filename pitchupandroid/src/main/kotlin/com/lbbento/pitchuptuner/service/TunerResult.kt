package com.lbbento.pitchuptuner.service

import com.lbbento.pitchupcore.TuningStatus

data class TunerResult(val note: String, val tuningStatus: TuningStatus, val expectedFrequency: Double, val diffFrequency: Double, val diffCents: Double)