package com.lbbento.pitchup.main

import com.lbbento.pitchup.common.StubAppScheduler
import com.lbbento.pitchup.service.TunerService
import com.lbbento.pitchup.util.PermissionHandler
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable

class MainPresenterTest {

    val mockPermissionHandler: PermissionHandler = mock()
    val mockView: MainView = mock()
    val mockTunerService: TunerService = mock()
    val stubAppSchedulers = StubAppScheduler()
    val mainPresenter = MainPresenter(stubAppSchedulers, mockPermissionHandler, mockTunerService)

    @Before
    fun setup() {
        mainPresenter.onAttachedToWindow(mockView)
    }

    @Test
    fun shouldDoNothingWhenNoAudioPermissions() {

        //Given
        whenever(mockPermissionHandler.handleMicrophonePermission()).thenReturn(false)

        //On
        mainPresenter.onViewResuming()

        //Should handle permissions
        verify(mockPermissionHandler).handleMicrophonePermission()

        //Should do nothing
        verify(mockTunerService, never()).getNotes()
    }

    @Test
    fun shouldStartTunerServiceWhenAudioPermissions() {

        //Given
        whenever(mockTunerService.getNotes()).thenReturn(Observable.empty())
        whenever(mockPermissionHandler.handleMicrophonePermission()).thenReturn(true)

        //On
        mainPresenter.onViewResuming()

        //should handle permissions
        verify(mockPermissionHandler).handleMicrophonePermission()

        //should start tuner service
        verify(mockTunerService).getNotes()
    }
}
