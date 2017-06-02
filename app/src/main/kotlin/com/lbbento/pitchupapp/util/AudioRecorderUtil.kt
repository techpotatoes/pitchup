package com.lbbento.pitchupapp.util

import android.media.AudioFormat
import android.media.AudioRecord

class AudioRecorderUtil {

    companion object {
        val SAMPLE_RATES = intArrayOf(44100)

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
}