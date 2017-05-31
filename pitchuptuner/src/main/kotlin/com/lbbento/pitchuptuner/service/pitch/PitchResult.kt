package com.lbbento.pitchuptuner.service.pitch

import com.lbbento.pitchuptuner.service.TuningStatus

internal data class PitchResult(val note: String, val tunningStatus: TuningStatus, val diff: Double)