package stream.alchemists.simplenoteapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.models.Note
import stream.alchemists.simplenoteapp.ui.viewmodel.NotesListViewModel

class NoteFormFragment : Fragment() {
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private lateinit var title: EditText
    private lateinit var description: EditText
    private val args: NoteFormFragmentArgs by navArgs()

    private val viewModel: NotesListViewModel by viewModels { NotesListViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_note_form, container, false)
        title = view.findViewById(R.id.form_note_title_edittext)
        description = view.findViewById(R.id.form_note_description_edittext)

        if (args.note != null) {
            title.setText(args.note!!.title)
            description.setText(args.note!!.description)
        }

        setUpMenu()
        return view
    }

    private fun setUpMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_note_form_save, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val note = if (args.note != null) {
                    Note(id = args.note!!.id,
                        title = title.text.toString(),
                        description = description.text.toString())
                } else {
                    Note(title = title.text.toString(),
                        description = description.text.toString())
                }
                mainScope.launch { viewModel.insert(note) }
                findNavController().popBackStack()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}