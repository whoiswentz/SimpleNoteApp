package stream.alchemists.simplenoteapp.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.dao.NotesDAO
import stream.alchemists.simplenoteapp.models.Note
import stream.alchemists.simplenoteapp.ui.recyclerview.holders.NoteViewHolder
import stream.alchemists.simplenoteapp.ui.recyclerview.listeners.OnItemClickItem
import java.util.Collections

class NoteListAdapter(private val notes: ArrayList<Note>) : RecyclerView.Adapter<NoteViewHolder>() {
    lateinit var onItemClickListener: OnItemClickItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount() = notes.size

    fun addNote(note: Note) {
        notes.add(note)
        notifyDataSetChanged()
    }

    fun updatePosition(position: Int, note: Note) {
        notes[position] = note
        notifyItemChanged(position)
    }

    fun remove(position: Int) {
        notes.removeAt(position)
        println(notes.size)
        notifyItemRemoved(position)
    }

    fun swap(initialPosition: Int, finalPosition: Int) {
        Collections.swap(notes, initialPosition, finalPosition)
        notifyItemMoved(initialPosition, finalPosition)
    }
}