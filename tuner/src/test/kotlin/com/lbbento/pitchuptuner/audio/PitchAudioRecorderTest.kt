package com.lbbento.pitchuptuner.audio

import android.media.AudioRecord
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class PitchAudioRecorderTest {

    val audioRecord: AudioRecord = mock()
    val pitchAudioRecorder = PitchAudioRecorder(audioRecord)

    @Test
    fun shouldStartRecording() {
        pitchAudioRecorder.startRecording()

        verify(audioRecord).startRecording()
    }

    @Test
    fun shouldStopRecording() {
        pitchAudioRecorder.stopRecording()

        verify(audioRecord).stop()
    }

}