package com.lbbento.pitchupcore.pitch

import com.lbbento.pitchupcore.InstrumentType.GUITAR
import com.lbbento.pitchupcore.TuningStatus.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PitchHandlerGuitarTest internal constructor(private val givenPitch: Float, private val expectedResult: PitchResult) {

    val pitchHandler = PitchHandler(GUITAR)

    companion object {
        val NOTE_E4 = 329.63f
        val NOTE_B3 = 246.94f
        val NOTE_G3 = 196.00f
        val NOTE_D3 = 146.83f
        val NOTE_A2 = 110.00f
        val NOTE_E2 = 82.41f
        val NOTE_E2_TOO_LOW = 82.10f
        val NOTE_E2_WAY_TOO_LOW = 81.40f
        val NOTE_E2_TOO_HIGH = 82.80f
        val NOTE_E2_WAY_TOO_HIGH = 83.50f

        val FREQUENCY_LESS_MINIMUM = 70.00f
        val FREQUENCY_MORE_MAXIMUM = 1100.00f

        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf(NOTE_E4, PitchResult("E", TUNED, NOTE_E4.toDouble(), 0.0, 0.0)),
                    arrayOf(NOTE_B3, PitchResult("B", TUNED, NOTE_B3.toDouble(), 0.0, 0.0)),
                    arrayOf(NOTE_G3, PitchResult("G", TUNED, NOTE_G3.toDouble(), 0.0, 0.0)),
                    arrayOf(NOTE_D3, PitchResult("D", TUNED, NOTE_D3.toDouble(), 0.0, 0.0)),
                    arrayOf(NOTE_A2, PitchResult("A", TUNED, NOTE_A2.toDouble(), 0.0, 0.0)),
                    arrayOf(NOTE_E2, PitchResult("E", TUNED, NOTE_E2.toDouble(), 0.0, 0.0)),

                    arrayOf(NOTE_E2_TOO_LOW, PitchResult("E", TOO_LOW, NOTE_E2.toDouble(), 0.31, 4.47)),
                    arrayOf(NOTE_E2_WAY_TOO_LOW, PitchResult("E", WAY_TOO_LOW, NOTE_E2.toDouble(), 1.01, 14.75)),

                    arrayOf(NOTE_E2_TOO_HIGH, PitchResult("E", TOO_HIGH, NOTE_E2.toDouble(), -0.39, -5.70)),
                    arrayOf(NOTE_E2_WAY_TOO_HIGH, PitchResult("E", WAY_TOO_HIGH, NOTE_E2.toDouble(), -1.10, -15.81)),

                    arrayOf(FREQUENCY_LESS_MINIMUM, PitchResult("", DEFAULT, 0.0, 0.0, 0.0)),
                    arrayOf(FREQUENCY_MORE_MAXIMUM, PitchResult("", DEFAULT, 0.0, 0.0, 0.0))

            )
        }
    }

    @Test
    fun shouldReturnExpectedNoteGivenPitch() {
        val result = pitchHandler.handlePitch(givenPitch)
        assertEquals(expectedResult.note, result.note)
    }

    @Test
    fun shouldReturnExpectedDiffGivenPitch() {
        val result = pitchHandler.handlePitch(givenPitch)
        assertEquals(expectedResult.diffFrequency, result.diffFrequency, 0.01)
    }

    @Test
    fun shouldReturnExpectedStatusGivenPitch() {
        val result = pitchHandler.handlePitch(givenPitch)
        assertEquals(expectedResult.tuningStatus, result.tuningStatus)
    }

    @Test
    fun shouldReturnExpectedFreqGivenFrequency() {
        val result = pitchHandler.handlePitch(givenPitch)
        assertEquals(expectedResult.expectedFrequency, result.expectedFrequency, 0.01)
    }

    @Test
    fun shouldReturnExpectedDiffCentsGivenFrequency() {
        val result = pitchHandler.handlePitch(givenPitch)
        assertEquals(expectedResult.diffCents, result.diffCents, 0.05)
    }
}