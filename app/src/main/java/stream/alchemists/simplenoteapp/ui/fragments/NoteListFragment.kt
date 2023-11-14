package stream.alchemists.simplenoteapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
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
import stream.alchemists.simplenoteapp.ui.viewmodel.LoginViewModel
import stream.alchemists.simplenoteapp.ui.viewmodel.NotesListViewModel


class NoteListFragment : Fragment() {
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private lateinit var notesList: RecyclerView
    private lateinit var insertNoteTextView: TextView

    private val viewModel: NotesListViewModel by viewModels { NotesListViewModel.Factory }
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModel.Factory }
    private val noteListAdapter: NoteListAdapter by lazy { NoteListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        insertNoteTextView = view.findViewById(R.id.list_notes_insert_textview)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView(view)
        setupMenu(view)
        insertNoteTextView.setOnClickListener {
            view.findNavController().navigate(R.id.navigate_to_note_form_fragment)
        }
    }

    private fun configureRecyclerView(view: View) {
        if (!loginViewModel.isLogged()) {
            val action = LoginFragmentDirections.navigateFromLoginToNotesList()
            view.findNavController().navigate(action)
        }

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
                view.findNavController().navigate(action)
            }
        }

        noteListAdapter.onSwipeToDelete = object : OnSwipeToDelete {
            override fun remove(note: Note) {
                mainScope.launch { viewModel.delete(note) }
            }
        }
    }

    private fun setupMenu(view: View) {
        val menuProvider = requireActivity() as MenuHost
        parentFragment?.view?.let {
            val navigation = view.findNavController()
            menuProvider.addMenuProvider(NoteListFragmentMenuProvider(navigation, loginViewModel))
        }
    }
}


class NoteListFragmentMenuProvider(
    private val navigation: NavController,
    private val loginViewModel: LoginViewModel
) : MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_list_logout, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_list_logout) {
            loginViewModel.logout()
            val action = NoteListFragmentDirections.actionGlobalLoginFragment()
            navigation.navigate(action)
        }
        return true
    }
}