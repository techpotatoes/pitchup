package com.lbbento.pitchuptuner

import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TunerService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import rx.Observable
import java.util.concurrent.TimeUnit.MILLISECONDS

class GuitarTunerReactiveTest {

    private val mockPitchAudioRecorder: PitchAudioRecorder = mock()
    private val mockGetNotesObservable: Observable<TunerResult> = mock {
        whenever(it.sample(700, MILLISECONDS)).thenReturn(it)
    }
    private val mockTunerService: TunerService = mock {
        whenever(it.getNotes()).thenReturn(mockGetNotesObservable)
    }

    private val guitarTunerReactive = GuitarTunerReactive(mockPitchAudioRecorder, mockTunerService)

    @Test
    fun shouldListenToNotes() {
        guitarTunerReactive.listenToNotes()

        verify(mockTunerService).getNotes()
        verify(mockGetNotesObservable).sample(700, MILLISECONDS)
    }

}