package stream.alchemists.simplenoteapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import stream.alchemists.simplenoteapp.dao.NotesDAO
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import stream.alchemists.simplenoteapp.SimpleNoteApp
import stream.alchemists.simplenoteapp.models.Note

class NotesListViewModel(
    private val notesDAO: NotesDAO
) : ViewModel() {

    fun all() = notesDAO.all()

    fun insert(vararg noteVararg: Note) = noteVararg.map { notesDAO.insert(it) }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val notesDao = (application as SimpleNoteApp).notesDAO
                return NotesListViewModel(notesDao) as T
            }
        }
    }
}