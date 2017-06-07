package com.lbbento.pitchupwear.main

import com.lbbento.pitchuptuner.GuitarTunerReactive
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchuptuner.service.TuningStatus.DEFAULT
import com.lbbento.pitchuptuner.service.TuningStatus.TOO_LOW
import com.lbbento.pitchupwear.common.StubAppScheduler
import com.lbbento.pitchupwear.util.PermissionHandler
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
    fun shouldSetAmbientEnabledOnCreate() {
        mainPresenter.onCreated()

        verify(mockView).setAmbientEnabled()
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
        }
        val setFreqTo = tunerViewModel.expectedFrequency + tunerViewModel.diffFrequency

        whenever(mockPermissionHandler.handleMicrophonePermission()).thenReturn(true)
        whenever(mockGuitarTunerReactive.listenToNotes()).thenReturn(just(tunerResult))
        whenever(mockMapper.tunerResultToViewModel(tunerResult)).thenReturn(tunerViewModel)

        mainPresenter.onViewResuming()

        verify(mockView).updateFrequency(setFreqTo.toFloat())
    }

    @Test
    fun shouldUpdateMaxAndMinFreqAndNoteIfNoteHasChanged() {
        val tunerResult: TunerResult = mock()
        val tunerViewModel: TunerViewModel = mock {
            whenever(it.tunningStatus).thenReturn(TOO_LOW)
            whenever(it.expectedFrequency).thenReturn(10.0)
            whenever(it.note).thenReturn("A")
        }
        val minFreq = tunerViewModel.expectedFrequency - 3f
        val maxFreq = tunerViewModel.expectedFrequency + 3f

        whenever(mockPermissionHandler.handleMicrophonePermission()).thenReturn(true)
        whenever(mockView.getCurrentNote()).thenReturn("B")
        whenever(mockGuitarTunerReactive.listenToNotes()).thenReturn(just(tunerResult))
        whenever(mockMapper.tunerResultToViewModel(tunerResult)).thenReturn(tunerViewModel)

        mainPresenter.onViewResuming()

        verify(mockView).updateMaxMinFreq(minFreq.toInt(), maxFreq.toInt())
        verify(mockView).updateToNote("A")
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
