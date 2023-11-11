package stream.alchemists.simplenoteapp.ui.recyclerview.listeners

import stream.alchemists.simplenoteapp.models.Note

interface OnItemClickItem {
    fun onItemClick(note: Note, position: Int)
}