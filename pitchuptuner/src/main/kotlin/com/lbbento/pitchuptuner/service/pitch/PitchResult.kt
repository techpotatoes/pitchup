package com.lbbento.pitchuptuner.service.pitch

internal data class PitchResult(val note: String, val tunningStatus: com.lbbento.pitchuptuner.service.TuningStatus, val diff: Double)