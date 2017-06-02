package com.lbbento.pitchupwear.util

import android.media.AudioFormat
import android.media.AudioRecord

class AudioRecorderUtil {

    companion object {
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
    }
}