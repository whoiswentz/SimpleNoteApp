package stream.alchemists.simplenoteapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import stream.alchemists.simplenoteapp.SimpleNoteApp
import stream.alchemists.simplenoteapp.dao.NotesDao
import stream.alchemists.simplenoteapp.models.Note

class NotesListViewModel(private val notesDao: NotesDao) : ViewModel() {
    fun all() = notesDao.all()

    suspend fun insert(vararg noteVararg: Note) = noteVararg.map { notesDao.insert(it) }

    suspend fun delete(note: Note) = notesDao.delete(note)

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val notesDao = (application as SimpleNoteApp).notesDao
                return NotesListViewModel(notesDao) as T
            }
        }
    }
}