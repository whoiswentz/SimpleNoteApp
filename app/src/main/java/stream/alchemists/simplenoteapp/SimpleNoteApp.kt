package stream.alchemists.simplenoteapp

import android.app.Application
import stream.alchemists.simplenoteapp.dao.NotesDAO

class SimpleNoteApp : Application() {
    lateinit var notesDAO: NotesDAO

    override fun onCreate() {
        super.onCreate()

        notesDAO = NotesDAO()
    }
}a