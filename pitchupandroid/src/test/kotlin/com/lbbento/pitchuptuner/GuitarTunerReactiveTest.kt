package com.lbbento.pitchuptuner

import com.lbbento.pitchupcore.TuningStatus
import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TunerService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.stub
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable.just
import org.junit.Test
import rx.Observable

class GuitarTunerReactiveTest {

    private val mockPitchAudioRecorder: PitchAudioRecorder = mock()
    private val tunerResult: TunerResult = mock() {
        whenever(it.tunningStatus).thenReturn(TuningStatus.TUNED)
    }
    private val mockGetNotesObservable: Observable<TunerResult> = mock {
        it.stub { observable -> just(tunerResult) }
    }
    private val mockTunerService: TunerService = mock {
        whenever(it.getNotes()).thenReturn(mockGetNotesObservable)
    }

    private val guitarTunerReactive = GuitarTunerReactive(mockPitchAudioRecorder, mockTunerService)

    @Test
    fun shouldListenToNotes() {
//        guitarTunerReactive.listenToNotes()
//
//        verify(mockTunerService).getNotes()
//        verify(mockGetNotesObservable).distinctUntilChanged({ anyString() }
//TODO
    }

}