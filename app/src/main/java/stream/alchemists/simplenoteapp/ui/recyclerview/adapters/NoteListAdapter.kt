package stream.alchemists.simplenoteapp.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.models.Note
import stream.alchemists.simplenoteapp.ui.recyclerview.holders.NoteViewHolder

class NoteListAdapter(private val notes: ArrayList<Note>) : RecyclerView.Adapter<NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.noteItemTitle.text = note.title
        holder.noteItemDescription.text = note.description
    }

    override fun getItemCount() = notes.size

    fun addNote(note: Note) {
        notes.add(note)
        notifyDataSetChanged()
    }
}