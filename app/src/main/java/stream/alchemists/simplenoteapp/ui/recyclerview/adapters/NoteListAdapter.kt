package stream.alchemists.simplenoteapp.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.models.Note
import stream.alchemists.simplenoteapp.ui.recyclerview.holders.NoteViewHolder
import stream.alchemists.simplenoteapp.ui.recyclerview.listeners.OnItemClickItem
import java.util.Collections

class NoteListAdapter(
    private val notes: ArrayList<Note> = arrayListOf(),
) : RecyclerView.Adapter<NoteViewHolder>() {
    lateinit var onItemClickListener: OnItemClickItem
    lateinit var onSwipeToDelete: OnSwipeToDelete

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount() = notes.size

    fun getItem(position: Int) = notes[position]

    fun addNote(note: Note) {
        notes.add(note)
        notifyDataSetChanged()
    }

    fun addAll(notes: Collection<Note>) {
        notifyItemRangeRemoved(0, this.notes.size)
        this.notes.clear()
        this.notes.addAll(notes)
        notifyItemRangeInserted(0, this.notes.size)
    }

    fun updatePosition(position: Int, note: Note) {
        notes[position] = note
        notifyItemChanged(position)
    }

    fun remove(position: Int) {
        val note = notes[position]
        notes.removeAt(position)
        onSwipeToDelete.remove(note)
        notifyItemRemoved(position)
    }

    fun swap(initialPosition: Int, finalPosition: Int) {
        Collections.swap(notes, initialPosition, finalPosition)
        notifyItemMoved(initialPosition, finalPosition)
    }
}