package stream.alchemists.simplenoteapp.ui.recyclerview.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.models.Note
import stream.alchemists.simplenoteapp.ui.recyclerview.listeners.OnItemClickItem

class NoteViewHolder(
    view: View,
    private val onItemClickItem: OnItemClickItem
) : RecyclerView.ViewHolder(view) {
    private val noteItemTitle: TextView
    private val noteItemDescription: TextView

    private lateinit var note: Note

    init {
        noteItemTitle = view.findViewById(R.id.note_item_card_title)
        noteItemDescription = view.findViewById(R.id.note_item_card_description)
        view.setOnClickListener { it ->
            onItemClickItem.onItemClick(note, adapterPosition)
        }
    }

    fun bind(note: Note) {
        this.note = note
        noteItemTitle.text = note.title
        noteItemDescription.text = note.description
    }
}