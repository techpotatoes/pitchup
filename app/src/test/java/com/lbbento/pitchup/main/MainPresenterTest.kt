package com.lbbento.pitchup.main

import com.lbbento.pitchup.util.PermissionHandler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    lateinit var view: MainView

    @Mock
    lateinit var permissionHandler: PermissionHandler

    @InjectMocks
    lateinit var presenter: MainPresenter

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
