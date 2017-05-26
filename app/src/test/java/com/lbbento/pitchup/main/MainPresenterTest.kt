package com.lbbento.pitchup.main

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    lateinit var view: MainView

    @InjectMocks
    lateinit var presenter: MainPresenter

    @Before
    fun setup() {
        presenter.onAttachedToWindow(view)
    }
}
