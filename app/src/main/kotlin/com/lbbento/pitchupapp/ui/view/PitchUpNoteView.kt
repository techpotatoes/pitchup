package com.lbbento.pitchupapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.lbbento.pitchupapp.R
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.view_pitchup_note.view.*


class PitchUpNoteView(context: Context, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) :
        FrameLayout(context, attrs, defStyle, defStyleRes), PitchUpNote {

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : this(context, attrs, defStyle, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)

    constructor(context: Context) : this(context, null, 0, 0)

    private val presenter = PitchUpNoteViewPresenter()
    private val notesAdapter = NotesAdapter(context)

    init {
        presenter.onAttachView(this)

        (context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.view_pitchup_note, this, true)

        view_pitchup_note_list.setItemTransformer(ScaleTransformer.Builder()
                .setMaxScale(2.50f)
                .setMinScale(0.8f)
                .setPivotX(Pivot.X.CENTER)
                .setPivotY(Pivot.Y.BOTTOM)
                .build())
        view_pitchup_note_list.adapter = notesAdapter

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        presenter.onFinishInflate()
    }

    override fun showNotesList(notesList: List<Map<String, String>>) {
        notesAdapter.addAll(notesList)
    }

    override fun scrollToPosition(position: Int) {
        view_pitchup_note_list.smoothScrollToPosition(position)
    }

    fun smoothScrollToNote(note: String) {
        presenter.onSmoothScrollToNote(note)
    }

}