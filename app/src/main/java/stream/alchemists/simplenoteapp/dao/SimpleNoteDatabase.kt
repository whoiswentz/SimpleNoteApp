package stream.alchemists.simplenoteapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import stream.alchemists.simplenoteapp.models.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class SimpleNoteDatabase : RoomDatabase() {
    abstract val notesDao: NotesDao

    companion object {
        fun getInstance(context: Context): SimpleNoteDatabase {
            return Room.databaseBuilder(
                context,
                SimpleNoteDatabase::class.java,
                "notes.db"
            ).build()
        }

        fun getNotesDao(context: Context): NotesDao {
            return getInstance(context).notesDao
        }
    }
}