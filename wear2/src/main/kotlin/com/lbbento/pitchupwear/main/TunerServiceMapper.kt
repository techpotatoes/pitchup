package com.lbbento.pitchupwear.main

import com.lbbento.pitchuptuner.service.TunerResult
import javax.inject.Inject

class TunerServiceMapper @Inject constructor() {
    fun tunerResultToViewModel(tunerResult: TunerResult) =
            TunerViewModel(tunerResult.note,
                    tunerResult.tunningStatus,
                    tunerResult.expectedFrequency,
                    tunerResult.diffFrequency,
                    tunerResult.diffCents)
}