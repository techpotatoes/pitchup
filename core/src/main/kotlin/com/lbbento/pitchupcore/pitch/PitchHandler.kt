package com.lbbento.pitchupcore.pitch

import com.lbbento.pitchupcore.InstrumentType
import com.lbbento.pitchupcore.InstrumentType.GUITAR
import com.lbbento.pitchupcore.TuningStatus.*
import kotlin.Double.Companion.NEGATIVE_INFINITY

class PitchHandler(instrumentType: InstrumentType) {

    private val MINIMUM_PITCH: Double
    private val MAXIMUM_PITCH: Double
    private val noteStrings: Array<String>

    init {
        when (instrumentType) {
            GUITAR -> {
                MINIMUM_PITCH = 80.0
                MAXIMUM_PITCH = 1050.0

                noteStrings = arrayOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")
            }
        }
    }

    fun handlePitch(pitch: Float): PitchResult {
        if (isPitchInRange(pitch)) {
            val noteLiteral = noteFromPitch(pitch)
            val expectedFrequency = frequencyFromNoteNumber(midiFromPitch(pitch))
            val diff = diffFromTargetedNote(pitch)
            val tuningStatus = tuningStatus(diff)
            val diffCents = diffInCents(expectedFrequency, expectedFrequency - diff)

            return PitchResult(noteLiteral, tuningStatus, expectedFrequency, diff, diffCents)
        }
        return PitchResult("", DEFAULT, 0.0, 0.0, 0.0)
    }

    private fun isPitchInRange(pitch: Float) = pitch > MINIMUM_PITCH && pitch < MAXIMUM_PITCH

    private fun noteFromPitch(frequency: Float): String {
        val noteNum = 12 * (Math.log((frequency / 440).toDouble()) / Math.log(2.0))
        return noteStrings[(Math.round(noteNum) + 69).toInt() % 12]
    }

    private fun diffFromTargetedNote(pitch: Float): Double {
        val targetPitch = frequencyFromNoteNumber(midiFromPitch(pitch))
        val diff = targetPitch - pitch
        return diff
    }

    private fun diffInCents(expectedFrequency: Double, frequency: Double) = 1200 * Math.log(expectedFrequency / frequency)

    private fun tuningStatus(diff: Double) = when (diff) {
        in -0.3..0.3 -> TUNED
        in -1.0..1.0 -> if (diff > 0) TOO_LOW else TOO_HIGH
        in NEGATIVE_INFINITY..-1.0 -> WAY_TOO_HIGH
        else -> WAY_TOO_LOW //greater than one
    }

    private fun midiFromPitch(frequency: Float): Int {
        val noteNum = 12 * (Math.log((frequency / 440).toDouble()) / Math.log(2.0))
        return (Math.round(noteNum) + 69).toInt()
    }

    private fun frequencyFromNoteNumber(note: Int): Double = 440 * Math.pow(2.0, (note - 69).toDouble() / 12.toDouble())
}