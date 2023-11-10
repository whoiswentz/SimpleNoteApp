package stream.alchemists.simplenoteapp.ui.recyclerview.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stream.alchemists.simplenoteapp.R

class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val noteItemTitle: TextView
    val noteItemDescription: TextView

    init {
        noteItemTitle = view.findViewById(R.id.note_item_card_title)
        noteItemDescription = view.findViewById(R.id.note_item_card_description)
    }
}