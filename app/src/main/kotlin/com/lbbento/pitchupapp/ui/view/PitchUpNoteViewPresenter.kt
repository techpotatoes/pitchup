package com.lbbento.pitchupapp.ui.view

internal class PitchUpNoteViewPresenter {
    val NOTE_PROPERTY = "note"

    val notes: List<String>
        get() = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")

    val adapterNotes: List<Map<String, String>>
        get() = notes.map { mapOf(NOTE_PROPERTY to it) }

    lateinit var pitchUpNoteView: PitchUpNote

    fun onAttachView(pitchUpNoteView: PitchUpNote) {
        this.pitchUpNoteView = pitchUpNoteView
    }

    fun onFinishInflate() {
        pitchUpNoteView.showNotesList(adapterNotes)
    }

    fun onSmoothScrollToNote(note: String) {
        when {
            notes.indexOf(note) >= 0 -> {
                pitchUpNoteView.scrollToPosition(notes.indexOf(note))
            }
        }
    }
}