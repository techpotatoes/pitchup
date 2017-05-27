package com.lbbento.pitchup.main

import com.lbbento.pitchup.util.PermissionHandler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    val permissionHandler: PermissionHandler
        get() = mock(PermissionHandler::class.java)

    val view: MainView
        get() = mock(MainView::class.java)

    val presenter: MainPresenter
        get() = MainPresenter(permissionHandler)

    @Before
    fun setup() {
        presenter.onAttachedToWindow(view)
    }

    @Test
    fun shouldCheckIfHaveRightPermissionsOnViewResuming() {
        presenter.onViewResuming()

        verify(permissionHandler).handleMicrophonePermission()
    }
}
