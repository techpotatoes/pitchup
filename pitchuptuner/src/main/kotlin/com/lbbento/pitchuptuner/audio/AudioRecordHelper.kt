package com.lbbento.pitchuptuner.audio

import android.media.AudioFormat.CHANNEL_IN_STEREO
import android.media.AudioFormat.ENCODING_PCM_16BIT
import android.media.AudioRecord.getMinBufferSize

class AudioRecordHelper {

    companion object {
        val SAMPLE_RATES = intArrayOf(8000, 11025, 16000, 22050, 32000, 37800, 44056, 44100, 47250, 48000)

        fun getSampleRate(): Int {
            var sampleRate = -1
            for (rate in SAMPLE_RATES) {
                val bufferSize = getMinBufferSize(rate, CHANNEL_IN_STEREO, ENCODING_PCM_16BIT)
                if (bufferSize > 0) {
                    sampleRate = rate
                }
            }
            return sampleRate
        }

        fun getReadSize(): Int {
            val minBufferSize = getMinBufferSize(getSampleRate(), CHANNEL_IN_STEREO, ENCODING_PCM_16BIT)
            return (minBufferSize / 4)
        }
    }
}