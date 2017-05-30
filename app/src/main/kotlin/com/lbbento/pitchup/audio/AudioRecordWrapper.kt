package com.lbbento.pitchup.audio

import android.media.AudioRecord
import com.lbbento.pitchup.audio.AudioRecordHelper.Companion.getReadSize
import javax.inject.Inject

class AudioRecordWrapper @Inject constructor(val audioRecord: AudioRecord) {

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