package com.lbbento.pitchup.main

import com.lbbento.pitchup.common.StubAppScheduler
import com.lbbento.pitchup.service.TunerService
import com.lbbento.pitchup.util.PermissionHandler
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.kotlintest.specs.BehaviorSpec
import rx.Observable

class MainPresenterTest : BehaviorSpec({
    val mockPermissionHandler: PermissionHandler = mock()
    val mockView: MainView = mock()
    val mockTunerService: TunerService = mock()
    val stubAppSchedulers = StubAppScheduler()

    given("MainPresenter") {

        whenever(mockTunerService.getNotes()).thenReturn(Observable.empty())

        val mainPresenter = MainPresenter(stubAppSchedulers, mockPermissionHandler, mockTunerService)

        `when`("Attaching to window") {
            mainPresenter.onAttachedToWindow(mockView)
        }

        `when`("resuming") {
            mainPresenter.onViewResuming()

            then("should check if it has audio recording permissions") {
                verify(mockPermissionHandler).handleMicrophonePermission()
            }

            then("should start tuner service") {
                verify(mockTunerService).getNotes()
            }
        }
    }
})
