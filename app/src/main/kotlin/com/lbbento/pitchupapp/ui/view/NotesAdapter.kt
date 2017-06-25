package com.lbbento.pitchupapp.ui.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lbbento.pitchupapp.R


class NotesAdapter(context: Context) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val items: ArrayList<Map<String, String>> = ArrayList()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(viewHolder: NotesViewHolder?, position: Int) {
        viewHolder!!.textNote.text = items[position]["note"]!!
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.view_pitchup_note_col, parent, false)

        return NotesViewHolder(view)
    }

    fun addAll(notesList: List<Map<String, String>>) {
        items.addAll(notesList)
        notifyDataSetChanged()
    }

    class NotesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textNote = view.findViewById<TextView>(R.id.view_pitchup_note_col_text)
    }
}