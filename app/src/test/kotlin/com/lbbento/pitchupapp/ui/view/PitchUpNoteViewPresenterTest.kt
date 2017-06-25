package com.lbbento.pitchupapp.ui.view

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

class PitchUpNoteViewPresenterTest {

    val notes: List<String>
        get() = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")

    val adapterNotes: List<Map<String, String>>
        get() = notes.map { mapOf("note" to it) }


    private val pitchUpNoteView: PitchUpNote = mock()
    private val pitchUpNotePresenter = PitchUpNoteViewPresenter()

    @Before
    fun setUp() {
        pitchUpNotePresenter.onAttachView(pitchUpNoteView)
    }

    @Test
    fun shouldInitializeAdapterOnInflatedWithAllNotes() {
        pitchUpNotePresenter.onFinishInflate()

        verify(pitchUpNoteView).showNotesList(adapterNotes)
    }

    @Test
    fun shouldScrollToPositionIfValid() {
        pitchUpNotePresenter.onSmoothScrollToNote("D")

        verify(pitchUpNoteView).scrollToPosition(2)
    }

    @Test
    fun shouldNotScrollToPositionIfInvalid() {
        pitchUpNotePresenter.onSmoothScrollToNote("J")

        verify(pitchUpNoteView, never()).scrollToPosition(anyInt())
    }

}