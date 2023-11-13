package stream.alchemists.simplenoteapp.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import stream.alchemists.simplenoteapp.models.Note
import java.util.Collections

@Dao
interface NotesDao {
    @Upsert
    suspend fun insert(note: Note)

    @Upsert
    suspend fun insert(notes: List<Note>)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun all(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun findById(id: Long): LiveData<Note?>
}