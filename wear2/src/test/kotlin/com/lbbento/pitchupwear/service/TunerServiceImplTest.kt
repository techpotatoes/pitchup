package com.lbbento.pitchupwear.service

import android.media.AudioRecord
import be.tarsos.dsp.pitch.Yin
import com.lbbento.pitchuptuner.audio.AudioRecordWrapper
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

class TunerServiceImplTest {

    val mockAudioRecord: AudioRecordWrapper = mock()
    val mockTorsoYin: Yin = mock()
    val tunerService = com.lbbento.pitchuptuner.service.TunerServiceImpl(mockAudioRecord, mockTorsoYin)

    @Test
    fun shouldDoNothingWhenStoppedOrFailedToStartRecording() {
        //given Tuner Service not recording
        whenever(mockAudioRecord.recordingState).thenReturn(AudioRecord.RECORDSTATE_STOPPED)

        //on getNotes
        tunerService.getNotes().subscribe()

        //should start recording
        verify(mockAudioRecord).startRecording()
        verify(mockAudioRecord).recordingState

        //should do nothing as it has failed
        verifyNoMoreInteractions(mockAudioRecord)

    }

    @Test
    fun shouldReadRecordedAudioWhenRecordingSuccessfully() {

        //given Tuner Service recording
        whenever(mockAudioRecord.recordingState).thenReturn(AudioRecord.RECORDSTATE_RECORDING)
        whenever(mockAudioRecord.read()).thenReturn(mock())

        //on getnotes

        tunerService.getNotes().subscribe()

        //should start recording"
        verify(mockAudioRecord).startRecording()

        //should read recorded com.lbbento.pitchuptuner.audio"
        verify(mockAudioRecord).read()

    }
}
