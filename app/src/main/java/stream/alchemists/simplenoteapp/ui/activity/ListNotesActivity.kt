package stream.alchemists.simplenoteapp.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.dao.NotesDAO
import stream.alchemists.simplenoteapp.models.Note
import stream.alchemists.simplenoteapp.ui.recyclerview.adapters.NoteListAdapter
import stream.alchemists.simplenoteapp.ui.recyclerview.helpers.callbacks.NoteItemTouchHelperCallback
import stream.alchemists.simplenoteapp.ui.recyclerview.listeners.OnItemClickItem
import stream.alchemists.simplenoteapp.ui.viewmodel.NotesListViewModel

class ListNotesActivity : AppCompatActivity() {
    private lateinit var notesList: RecyclerView
    private lateinit var insertNoteTextView: TextView
    private lateinit var noteListAdapter: NoteListAdapter

    private val viewModel: NotesListViewModel by viewModels { NotesListViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)

        for (i in 0..5) {
            viewModel.insert(Note("Title $i", "Description $i"))
        }

        findActivityElements()
        configureRecyclerView()

        setupInsertNoteTextViewListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val note = data?.getSerializableExtra("note", Note::class.java)
        val position = data?.getIntExtra("position", -1)

        if (requestCode == 1) {
            if (note != null) {
                noteListAdapter.addNote(note)
            }
        }
        if (requestCode == 2 && position != -1) {
            if (note != null && position != null) {
                noteListAdapter.updatePosition(position, note)
            }
        }
    }

    private fun findActivityElements() {
        notesList = findViewById(R.id.list_notes_recyclerview)
        insertNoteTextView = findViewById(R.id.list_notes_insert_textview)
    }

    private fun configureRecyclerView() {
        noteListAdapter = NoteListAdapter(viewModel.all())
        notesList.layoutManager = LinearLayoutManager(this)
        notesList.adapter = noteListAdapter

        val itemTouchHelper = ItemTouchHelper(NoteItemTouchHelperCallback(noteListAdapter))
        itemTouchHelper.attachToRecyclerView(notesList)

        val intent = Intent(this, NoteFormActivity::class.java)
        noteListAdapter.onItemClickListener = object : OnItemClickItem {
            override fun onItemClick(note: Note, position: Int) {
                intent.putExtra("note", note)
                intent.putExtra("position", position)
                startActivityForResult(intent, 2)
            }
        }
    }

    private fun setupInsertNoteTextViewListener() {
        insertNoteTextView.setOnClickListener { _ ->
            val intent = Intent(this, NoteFormActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }
}
