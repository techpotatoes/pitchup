package com.lbbento.pitchuptuner

import com.lbbento.pitchupcore.TuningStatus
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TunerService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import rx.Observable.just

class GuitarTunerReactiveTest {

    private val mockPitchAudioRecorder: PitchAudioRecorder = mock()
    private val tunerResult: TunerResult = mock {
        whenever(it.tuningStatus).thenReturn(TuningStatus.TUNED)
        whenever(it.note).thenReturn("A")
    }
    private val mockTunerService: TunerService = mock {
        whenever(it.getNotes()).thenReturn(just(tunerResult))
    }

    private val guitarTunerReactive = GuitarTunerReactive(mockPitchAudioRecorder, mockTunerService)

    @Test
    fun shouldListenToNotes() {
        guitarTunerReactive.listenToNotes()

        verify(mockTunerService).getNotes()
    }

}