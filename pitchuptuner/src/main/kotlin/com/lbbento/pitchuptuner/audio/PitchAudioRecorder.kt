package com.lbbento.pitchuptuner.audio

import android.media.AudioFormat
import android.media.AudioRecord

class PitchAudioRecorder @javax.inject.Inject constructor(private val audioRecord: AudioRecord) {

    companion object {
        //VALID SAMPLERATES (8000, 11025, 16000, 22050, 32000, 37800, 44056, 44100, 47250, 48000)
        val SAMPLE_RATES = intArrayOf(8000, 11025, 16000)

        fun getSampleRate(): Int {
            var sampleRate = -1
            for (rate in SAMPLE_RATES) {
                val bufferSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT)
                if (bufferSize > 0) {
                    sampleRate = rate
                }
            }
            return sampleRate
        }

        fun getReadSize(): Int {
            val minBufferSize = AudioRecord.getMinBufferSize(getSampleRate(), AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT)
            return (minBufferSize / 4)
        }
    }

    val recordingState: Int
        get() = audioRecord.recordingState

    fun startRecording() {
        audioRecord.startRecording()
    }

    fun read(): FloatArray {
        val audioData = ShortArray(getReadSize())
        audioRecord.read(audioData, 0, getReadSize())

        val buffer = FloatArray(audioData.size)
        audioData.indices.forEach { i -> buffer[i] = audioData[i].toFloat() }

        return buffer
    }
}