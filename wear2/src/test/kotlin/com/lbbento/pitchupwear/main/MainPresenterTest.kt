package com.lbbento.pitchupwear.main

import com.lbbento.pitchupcore.TuningStatus.DEFAULT
import com.lbbento.pitchupcore.TuningStatus.TOO_LOW
import com.lbbento.pitchuptuner.GuitarTunerReactive
import com.lbbento.pitchuptuner.service.TunerResult
import com.lbbento.pitchupwear.common.StubAppScheduler
import com.lbbento.pitchupwear.util.PermissionHelper
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.Observable.just
import rx.Subscription

class MainPresenterTest {

    val mockPermissionHelper: PermissionHelper = mock()
    val mockView: MainView = mock()
    val mockGuitarTunerReactive: GuitarTunerReactive = mock()
    val mockMapper: TunerServiceMapper = mock()
    val stubAppSchedulers = StubAppScheduler()
    val mainPresenter = MainPresenter(stubAppSchedulers, mockPermissionHelper, mockGuitarTunerReactive, mockMapper)

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
        whenever(mockPermissionHelper.handleMicrophonePermission()).thenReturn(false)

        mainPresenter.onViewResuming()

        verify(mockPermissionHelper).handleMicrophonePermission()
        verify(mockGuitarTunerReactive, never()).listenToNotes()
    }

    @Test
    fun shouldUpdateToDefaultStatus() {
        val tunerResult: TunerResult = mock()
        val tunerViewModel: TunerViewModel = mock {
            whenever(it.tuningStatus).thenReturn(DEFAULT)
        }

        whenever(mockPermissionHelper.handleMicrophonePermission()).thenReturn(true)
        whenever(mockGuitarTunerReactive.listenToNotes()).thenReturn(just(tunerResult))
        whenever(mockMapper.tunerResultToViewModel(tunerResult)).thenReturn(tunerViewModel)

        mainPresenter.onViewResuming()

        verify(mockView).updateToDefaultStatus()
    }

    @Test
    fun shouldUpdateFrequencyIfStatusIsNotDefault() {
        val tunerResult: TunerResult = mock()
        val tunerViewModel: TunerViewModel = mock {
            whenever(it.tuningStatus).thenReturn(TOO_LOW)
            whenever(it.expectedFrequency).thenReturn(10.0)
            whenever(it.diffFrequency).thenReturn(1.0)
            whenever(it.diffInCents).thenReturn(11.0)
            whenever(it.note).thenReturn("A")
        }
        val setFreqTo = (tunerViewModel.expectedFrequency + (tunerViewModel.diffFrequency * -1)).toFloat()

        whenever(mockPermissionHelper.handleMicrophonePermission()).thenReturn(true)
        whenever(mockGuitarTunerReactive.listenToNotes()).thenReturn(just(tunerResult))
        whenever(mockMapper.tunerResultToViewModel(tunerResult)).thenReturn(tunerViewModel)

        mainPresenter.onViewResuming()

        verify(mockView).updateIndicator(-11f)
        verify(mockView).updateNote(tunerViewModel.note)
        verify(mockView).updateCurrentFrequency(setFreqTo)
    }

    @Test
    fun shouldUpdateTunerViewWhenReceivedErrorFromService() {
        whenever(mockPermissionHelper.handleMicrophonePermission()).thenReturn(true)
        whenever(mockGuitarTunerReactive.listenToNotes()).thenReturn(Observable.error(IllegalStateException()))

        mainPresenter.onViewResuming()

        verify(mockPermissionHelper).handleMicrophonePermission()
        verify(mockGuitarTunerReactive).listenToNotes()
        verify(mockView).informError()
    }

    @Test
    fun shouldUnsubscribeOnStop() {
        val mockSubscription: Subscription = mock()
        mainPresenter.tunerServiceSubscription = mockSubscription

        mainPresenter.onStop()

        verify(mockSubscription).unsubscribe()
    }
}
