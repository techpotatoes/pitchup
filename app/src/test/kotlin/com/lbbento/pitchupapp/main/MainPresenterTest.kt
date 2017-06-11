package com.lbbento.pitchupapp.main

import com.lbbento.pitchupapp.common.StubAppScheduler
import com.lbbento.pitchupapp.util.PermissionHandler
import com.lbbento.pitchupcore.TuningStatus.DEFAULT
import com.lbbento.pitchupcore.TuningStatus.TOO_LOW
import com.lbbento.pitchuptuner.GuitarTunerReactive
import com.lbbento.pitchuptuner.service.TunerResult
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.Observable.just

class MainPresenterTest {

    val mockPermissionHandler: PermissionHandler = mock()
    val mockView: MainView = mock()
    val mockGuitarTunerReactive: GuitarTunerReactive = mock()
    val mockMapper: TunerServiceMapper = mock()
    val stubAppSchedulers = StubAppScheduler()
    val mainPresenter = MainPresenter(stubAppSchedulers, mockPermissionHandler, mockGuitarTunerReactive, mockMapper)

    @Before
    fun setup() {
        mainPresenter.onAttachedToWindow(mockView)
    }

    @Test
    fun shouldSetupGaugeOnCreate() {
        mainPresenter.onCreated()

        verify(mockView).setupGauge()
    }

    @Test
    fun shouldDoNothingWhenNoAudioPermissions() {
        whenever(mockPermissionHandler.handleMicrophonePermission()).thenReturn(false)

        mainPresenter.onViewResuming()

        verify(mockPermissionHandler).handleMicrophonePermission()
        verify(mockGuitarTunerReactive, never()).listenToNotes()
    }

    @Test
    fun shouldUpdateToDefaultStatus() {
        val tunerResult: TunerResult = mock()
        val tunerViewModel: TunerViewModel = mock {
            whenever(it.tunningStatus).thenReturn(DEFAULT)
        }

        whenever(mockPermissionHandler.handleMicrophonePermission()).thenReturn(true)
        whenever(mockGuitarTunerReactive.listenToNotes()).thenReturn(just(tunerResult))
        whenever(mockMapper.tunerResultToViewModel(tunerResult)).thenReturn(tunerViewModel)

        mainPresenter.onViewResuming()

        verify(mockView).updateToDefaultStatus()
    }

    @Test
    fun shouldUpdateFrequencyIfStatusIsNotDefault() {
        val tunerResult: TunerResult = mock()
        val tunerViewModel: TunerViewModel = mock {
            whenever(it.tunningStatus).thenReturn(TOO_LOW)
            whenever(it.expectedFrequency).thenReturn(10.0)
            whenever(it.diffFrequency).thenReturn(1.0)
            whenever(it.diffInCents).thenReturn(11.0)
            whenever(it.note).thenReturn("A")
        }
        val setFreqTo = (tunerViewModel.expectedFrequency + (tunerViewModel.diffFrequency * -1)).toFloat()

        whenever(mockPermissionHandler.handleMicrophonePermission()).thenReturn(true)
        whenever(mockGuitarTunerReactive.listenToNotes()).thenReturn(just(tunerResult))
        whenever(mockMapper.tunerResultToViewModel(tunerResult)).thenReturn(tunerViewModel)

        mainPresenter.onViewResuming()

        verify(mockView).updateIndicator(-11f)
        verify(mockView).updateNote(tunerViewModel.note)
        verify(mockView).updateCurrentFrequency(setFreqTo)
    }

    @Test
    fun shouldUpdateTunerViewWhenReceivedErrorFromService() {
        whenever(mockPermissionHandler.handleMicrophonePermission()).thenReturn(true)
        whenever(mockGuitarTunerReactive.listenToNotes()).thenReturn(Observable.error(IllegalStateException()))

        mainPresenter.onViewResuming()

        verify(mockPermissionHandler).handleMicrophonePermission()
        verify(mockGuitarTunerReactive).listenToNotes()
        verify(mockView).informError()
    }
}
