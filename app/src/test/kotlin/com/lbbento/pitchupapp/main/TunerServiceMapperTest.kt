package com.lbbento.pitchupapp.main

import com.lbbento.pitchupcore.TuningStatus.TUNED
import com.lbbento.pitchuptuner.service.TunerResult
import org.junit.Assert.assertEquals
import org.junit.Test

class TunerServiceMapperTest {

    val tunerServiceMapper = TunerServiceMapper()

    @Test
    fun tunerResultToViewModel() {
        val tunerResult = TunerResult("A", TUNED, 100.0, 20.0, 10.0)
        val expectedTunerViewModel = TunerViewModel("A", TUNED, 100.0, 20.0, 10.0)

        assertEquals(expectedTunerViewModel, tunerServiceMapper.tunerResultToViewModel(tunerResult))
    }

}