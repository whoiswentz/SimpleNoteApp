package stream.alchemists.simplenoteapp

import android.app.Application
import stream.alchemists.simplenoteapp.dao.NotesDao
import stream.alchemists.simplenoteapp.dao.SimpleNoteDatabase

class SimpleNoteApp : Application() {
    val notesDao: NotesDao
        get() = SimpleNoteDatabase.getNotesDao(this)

    override fun onCreate() {
        super.onCreate()
    }
}