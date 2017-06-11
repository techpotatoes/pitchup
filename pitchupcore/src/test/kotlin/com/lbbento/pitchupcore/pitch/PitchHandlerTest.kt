package com.lbbento.pitchupcore.pitch

import com.lbbento.pitchupcore.InstrumentType.GUITAR
import com.lbbento.pitchupcore.TuningStatus
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PitchHandlerGuitarTest internal constructor(private val givenPitch: Float, private val expectedResult: PitchResult) {

    val pitchHandler = PitchHandler(GUITAR)

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf(82.41, PitchResult("E", TuningStatus.TUNED, 82.41, 0.0, 0.0))
            )
        }
    }

    @Test
    fun shouldReturnExpectedNoteGivenFrequency() {
        val result = pitchHandler.handlePitch(givenPitch)
        assertEquals(result.note, expectedResult.note)
    }

}