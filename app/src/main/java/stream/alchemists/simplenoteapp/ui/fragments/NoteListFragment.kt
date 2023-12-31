package stream.alchemists.simplenoteapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.models.Note
import stream.alchemists.simplenoteapp.ui.recyclerview.adapters.NoteListAdapter
import stream.alchemists.simplenoteapp.ui.recyclerview.adapters.OnSwipeToDelete
import stream.alchemists.simplenoteapp.ui.recyclerview.helpers.callbacks.NoteItemTouchHelperCallback
import stream.alchemists.simplenoteapp.ui.recyclerview.listeners.OnItemClickItem
import stream.alchemists.simplenoteapp.ui.viewmodel.NotesListViewModel


class NoteListFragment : Fragment() {
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private lateinit var notesList: RecyclerView
    private lateinit var insertNoteTextView: TextView

    private val viewModel: NotesListViewModel by viewModels { NotesListViewModel.Factory }
    private val noteListAdapter: NoteListAdapter by lazy { NoteListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        configureRecyclerView(view)

        insertNoteTextView = view.findViewById(R.id.list_notes_insert_textview)
        insertNoteTextView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigate_to_note_form_fragment)
        }

        return view
    }

    private fun configureRecyclerView(view: View) {
        notesList = view.findViewById(R.id.list_notes_recyclerview)
        viewModel.all().observe(viewLifecycleOwner) {
            noteListAdapter.addAll(it)
        }

        notesList.apply {
            adapter = noteListAdapter
        }

        val itemTouchHelper = ItemTouchHelper(NoteItemTouchHelperCallback(noteListAdapter))
        itemTouchHelper.attachToRecyclerView(notesList)

        noteListAdapter.onItemClickListener = object : OnItemClickItem {
            override fun onItemClick(note: Note, position: Int) {
                val action = NoteListFragmentDirections.navigateToNoteFormFragment(position, note)
                Navigation.findNavController(view).navigate(action)
            }
        }

        noteListAdapter.onSwipeToDelete = object : OnSwipeToDelete {
            override fun remove(note: Note) {
                mainScope.launch { viewModel.delete(note) }
            }
        }
    }
}