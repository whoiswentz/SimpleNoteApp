package stream.alchemists.simplenoteapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import stream.alchemists.simplenoteapp.dao.NotesDao
import stream.alchemists.simplenoteapp.dao.SimpleNoteDatabase

class SimpleNoteApp : Application() {
    val notesDao: NotesDao
        get() = SimpleNoteDatabase.getNotesDao(this)

    val sharedPreferences: SharedPreferences
        get() = getSharedPreferences("login", Context.MODE_PRIVATE)
}