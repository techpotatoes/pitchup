package com.lbbento.pitchup.main

import com.lbbento.pitchup.util.PermissionHandler
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.kotlintest.specs.BehaviorSpec

class MainPresenterTest : BehaviorSpec({

    given("MainPresenter") {
        val permissionHandler: PermissionHandler = mock()
        val view: MainView = mock()
        val mainPresenter = MainPresenter(permissionHandler)
        mainPresenter.onAttachedToWindow(view)

        `when`("resuming") {
            mainPresenter.onViewResuming()

            then("should check if it has audio recording permissions") {
                verify(permissionHandler).handleMicrophonePermission()
            }
        }
    }
})
