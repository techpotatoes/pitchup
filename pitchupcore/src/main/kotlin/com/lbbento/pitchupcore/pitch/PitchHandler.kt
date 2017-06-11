package com.lbbento.pitchupcore.pitch

import com.lbbento.pitchupcore.InstrumentType
import com.lbbento.pitchupcore.InstrumentType.GUITAR
import com.lbbento.pitchupcore.TuningStatus
import com.lbbento.pitchupcore.TuningStatus.*

class PitchHandler(instrumentType: InstrumentType) {

    private val MINIMUN_PITCH: Double
    private val MAXIMUM_PITCH: Double
    private val noteStrings: Array<String>

    init {
        when (instrumentType) {
            GUITAR -> {
                MINIMUN_PITCH = 80.0
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

    private fun isPitchInRange(pitch: Float) = pitch > MINIMUN_PITCH && pitch < MAXIMUM_PITCH

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

    private fun tuningStatus(diff: Double): TuningStatus {
        if (diff < 0.3 && diff > -0.3) {
            return TUNED
        } else if (diff < 1 && diff > -1) {
            if (diff > 0) {
                return TOO_LOW
            } else {
                return TOO_HIGH
            }
        } else if (diff > 1) {
            return WAY_TOO_LOW
        } else if (diff < -1) {
            return WAY_TOO_HIGH
        }
        return DEFAULT
    }

    private fun midiFromPitch(frequency: Float): Int {
        val noteNum = 12 * (Math.log((frequency / 440).toDouble()) / Math.log(2.0))
        return (Math.round(noteNum) + 69).toInt()
    }

    private fun frequencyFromNoteNumber(note: Int): Double = 440 * Math.pow(2.0, (note - 69).toDouble() / 12.toDouble())
}