package com.lbbento.pitchuptuner.service

import android.media.AudioRecord.RECORDSTATE_RECORDING
import android.util.Log
import be.tarsos.dsp.pitch.Yin
import com.lbbento.pitchuptuner.audio.AudioRecordWrapper
import rx.Observable.create


class TunerServiceImpl @javax.inject.Inject constructor(val audioRecord: AudioRecordWrapper, val torsoYin: Yin) : TunerService {

    private val noteStrings = arrayOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")

    override fun getNotes(): rx.Observable<TunerResult> {
        return create<TunerResult>({
            audioRecord.startRecording()

            while (audioRecord.recordingState == RECORDSTATE_RECORDING) {
                val buffer = audioRecord.read()

                val pitchResult = torsoYin.getPitch(buffer)

                handlePitch(pitchResult.pitch)
            }
        })
    }

    private fun handlePitch(pitch: Float) {
        if (pitch > 80.0 && pitch < 1050.0) {
            val noteLiteral = noteFromPitch(pitch)
            val targetPitch = frequencyFromNoteNumber(midiFromPitch(pitch))
            val diff = targetPitch - pitch

            Log.i("Lucas", String.format("Note: %s", noteLiteral))

            if (diff < 0.3 && diff > -0.3)
                Log.i("Lucas", (String.format("You are tuned to %s", noteLiteral)))
            else if (diff < 1 && diff > -1) {
                if (diff > 0)
                    Log.i("Lucas", (String.format("Almost tuned, a little up to %s", noteLiteral)))
                else
                    Log.i("Lucas", (String.format("Almost tuned, a little down to %s", noteLiteral)))
            } else if (diff > 1)
                Log.i("Lucas", (String.format("Too flat! tune up a bit. Tuning %s", noteLiteral)))
            else if (diff < -1) Log.i("Lucas", (String.format("Too sharp! tune down a bit. Tuning %s", noteLiteral)))

            Log.d("Lucas", "Note: " + noteFromPitch(pitch) + ", Current Pitch: " + pitch + "Note num: " + midiFromPitch(pitch) + "Note's Pitch: " + frequencyFromNoteNumber(midiFromPitch(pitch)))
        }
    }

    private fun noteFromPitch(frequency: Float): String {
        val noteNum = 12 * (Math.log((frequency / 440).toDouble()) / Math.log(2.0))
        return noteStrings[(Math.round(noteNum) + 69).toInt() % 12]
    }

    private fun frequencyFromNoteNumber(note: Int): Double {
        return 440 * Math.pow(2.0, (note - 69).toDouble() / 12.toDouble())
    }

    private fun midiFromPitch(frequency: Float): Int {
        val noteNum = 12 * (Math.log((frequency / 440).toDouble()) / Math.log(2.0))
        return (Math.round(noteNum) + 69).toInt()
    }
}