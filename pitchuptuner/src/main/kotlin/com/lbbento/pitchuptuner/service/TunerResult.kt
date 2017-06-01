package com.lbbento.pitchuptuner.service

data class TunerResult(val note: String, val tunningStatus: TuningStatus, val expectedFrequency: Double, val diffFrequency: Double)