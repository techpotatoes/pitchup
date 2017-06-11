package com.lbbento.pitchuptuner

import com.lbbento.pitchuptuner.audio.PitchAudioRecorder
import com.lbbento.pitchuptuner.common.StubAppScheduler
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TunerService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import rx.Observable
import rx.Observable.error
import rx.Observable.just
import rx.Subscription

class GuitarTunerTest {

    private val mockPitchAudioRecord: PitchAudioRecorder = mock()
    private val mockGuitarTunerListener: GuitarTunerListener = mock()
    private val mockTunerService: TunerService = mock()
    private val appSchedulers: AppSchedulers = StubAppScheduler()

    val guitarTuner: GuitarTuner = GuitarTuner(mockPitchAudioRecord, mockGuitarTunerListener, mockTunerService, appSchedulers)

    @Test
    fun shouldSubscribeToGetNotesService() {
        val mockObservable: Observable<TunerResult> = mock()

        whenever(mockTunerService.getNotes()).thenReturn(mockObservable)
        whenever(mockObservable.observeOn(appSchedulers.ui())).thenReturn(mockObservable)
        whenever(mockObservable.subscribeOn(appSchedulers.computation())).thenReturn(mockObservable)
        whenever(mockObservable.sample(any(), any())).thenReturn(mockObservable)

        guitarTuner.start()

        verify(mockTunerService).getNotes()
        verify(mockObservable).observeOn(appSchedulers.ui())
        verify(mockObservable).subscribeOn(appSchedulers.computation())
        verify(mockObservable).subscribe(any(), any())
    }

    @Test
    fun shouldUnsubscribeFromGetNotesService() {
        val mockObservable: Observable<TunerResult> = mock()
        val mockSubscription: Subscription = mock()

        whenever(mockTunerService.getNotes()).thenReturn(mockObservable)
        whenever(mockObservable.observeOn(appSchedulers.ui())).thenReturn(mockObservable)
        whenever(mockObservable.subscribeOn(appSchedulers.computation())).thenReturn(mockObservable)
        whenever(mockObservable.sample(any(), any())).thenReturn(mockObservable)
        whenever(mockObservable.subscribe(any(), any())).thenReturn(mockSubscription)

        guitarTuner.start()

        guitarTuner.stop()

        verify(mockSubscription).unsubscribe()
    }

    @Test
    fun shouldCallOnNoteReceivedListener() {
        val tunerResult: TunerResult = mock()

        whenever(mockTunerService.getNotes()).thenReturn(just(tunerResult))

        guitarTuner.start()

        verify(mockGuitarTunerListener).onNoteReceived(tunerResult)
    }

    @Test
    fun shouldCallOnErrorListener() {
        val expectedException = Exception()

        whenever(mockTunerService.getNotes()).thenReturn(error(expectedException))

        guitarTuner.start()

        verify(mockGuitarTunerListener).onError(expectedException)
    }
}