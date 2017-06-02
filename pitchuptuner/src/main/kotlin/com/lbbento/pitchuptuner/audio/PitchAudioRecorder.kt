package com.lbbento.pitchuptuner.audio

import android.media.AudioFormat.CHANNEL_IN_STEREO
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioRecord
import android.media.AudioRecord.getMinBufferSize

class PitchAudioRecorder(private val audioRecord: AudioRecord) {

    val recordingState: Int
        get() = audioRecord.recordingState

    val sampleRateInHz: Int
        get() = audioRecord.sampleRate

    val readSize: Int
        get() = getMinBufferSize(sampleRateInHz, CHANNEL_IN_STEREO, ENCODING_PCM_16BIT) / 4

    fun startRecording() {
        audioRecord.startRecording()
    }

    fun read(): FloatArray {
        val audioData = ShortArray(readSize)
        audioRecord.read(audioData, 0, sampleRateInHz)

        val buffer = FloatArray(audioData.size)
        audioData.indices.forEach { i -> buffer[i] = audioData[i].toFloat() }

        return buffer
    }
}