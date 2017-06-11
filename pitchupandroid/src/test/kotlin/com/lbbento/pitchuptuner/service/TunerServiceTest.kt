package com.lbbento.pitchuptuner.service

import android.media.AudioRecord.RECORDSTATE_RECORDING
import android.media.AudioRecord.RECORDSTATE_STOPPED
import be.tarsos.dsp.pitch.PitchDetectionResult
import be.tarsos.dsp.pitch.Yin
import com.lbbento.pitchupcore.InstrumentType.GUITAR
import com.lbbento.pitchupcore.TuningStatus.TUNED
import com.lbbento.pitchupcore.pitch.PitchHandler
import com.lbbento.pitchupcore.pitch.PitchResult
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import rx.observers.TestSubscriber

private class TestableTunerService(pitchAudioRecorder: PitchAudioRecorder, override val torsoYin: Yin, override val pitchHandler: PitchHandler) : TunerService(pitchAudioRecorder, GUITAR)

class TunerServiceTest {
    val mockTorsoYin: Yin = mock()
    val pitchHandler: PitchHandler = mock()
    val mockPitchAudioRecord: PitchAudioRecorder = mock()

    private val tunerService = TestableTunerService(mockPitchAudioRecord, mockTorsoYin, pitchHandler)

    @Test
    fun shouldCallOnCompleteWhenStoppedOrFailedToStartRecording() {
        //given Tuner Service not recording
        whenever(mockPitchAudioRecord.recordingState).thenReturn(RECORDSTATE_STOPPED)

        //on getNotes
        val testSubscriber = TestSubscriber<TunerResult>()
        tunerService.getNotes().subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent()

        //should start recording
        verify(mockPitchAudioRecord).startRecording()
        verify(mockPitchAudioRecord).recordingState

        //should do nothing as it has failed
        testSubscriber.assertTerminalEvent()
    }

    @Test
    fun shouldReturnCorrectResultWhenRecordingSuccessfully() {
        val buffer = FloatArray(0)
        val pitchDetectionResult = mock<PitchDetectionResult>()
        val pitchResult = mock<PitchResult>()

        //given Tuner Service recording

        whenever(mockPitchAudioRecord.recordingState).thenReturn(RECORDSTATE_RECORDING)
        whenever(mockPitchAudioRecord.read()).thenReturn(buffer)
        whenever(mockTorsoYin.getPitch(buffer)).thenReturn(pitchDetectionResult)
        whenever(pitchDetectionResult.pitch).thenReturn(123F)
        whenever(pitchHandler.handlePitch(123F)).thenReturn(pitchResult)
        whenever(pitchResult.note).thenReturn("E")
        whenever(pitchResult.tunningStatus).thenReturn(TUNED)
        whenever(pitchResult.diffFrequency).thenReturn(3.3)
        whenever(pitchResult.expectedFrequency).thenReturn(3.0)
        whenever(pitchResult.diffCents).thenReturn(10.0)

        //on getnotes
        val testSubscriber = TestSubscriber<TunerResult>()
        tunerService.getNotes()
                .doOnNext({ whenever(mockPitchAudioRecord.recordingState).thenReturn(RECORDSTATE_STOPPED) })
                .subscribe(testSubscriber)
        testSubscriber.awaitTerminalEvent()

        //should
        verify(mockPitchAudioRecord).startRecording()
        verify(mockPitchAudioRecord).read()
        verify(mockTorsoYin).getPitch(buffer)
        verify(pitchHandler).handlePitch(123F)
        assertEquals(testSubscriber.onNextEvents[0], TunerResult("E", TUNED, 3.0, 3.3, 10.0))
    }
}
