package com.lbbento.pitchupapp.ui.view

internal interface PitchUpNote {
    fun showNotesList(notesList: List<Map<String, String>>)
    fun scrollToPosition(position: Int)
}