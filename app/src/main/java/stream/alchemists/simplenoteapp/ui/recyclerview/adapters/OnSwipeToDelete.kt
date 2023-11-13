package stream.alchemists.simplenoteapp.ui.recyclerview.adapters

import stream.alchemists.simplenoteapp.models.Note

interface OnSwipeToDelete {
    fun remove(note: Note)
}