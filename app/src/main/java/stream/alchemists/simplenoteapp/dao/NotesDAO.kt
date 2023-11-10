package stream.alchemists.simplenoteapp.dao

import stream.alchemists.simplenoteapp.models.Note
import java.util.Collections

class NotesDAO {
    companion object {
        private val notes: ArrayList<Note> = ArrayList()
    }

    fun all() = notes
    fun insert(vararg noteVararg: Note) = notes.addAll(noteVararg)
    fun remove(position: Int) = notes.removeAt(position)
    fun swap(position1: Int, position2: Int) = Collections.swap(notes, position1, position2)
    fun clear() = notes.clear()

    fun set(position: Int, note: Note) {
        notes[position] = note
    }
}