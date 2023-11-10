package stream.alchemists.simplenoteapp.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.dao.NotesDAO
import stream.alchemists.simplenoteapp.models.Note
import stream.alchemists.simplenoteapp.ui.recyclerview.adapters.NoteListAdapter

class ListNotesActivity : AppCompatActivity() {
    private lateinit var notesList: RecyclerView
    private lateinit var insertNoteTextView: TextView
    private lateinit var noteListAdapter: NoteListAdapter

    private val noteDao = NotesDAO()

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val note = result.data?.getSerializableExtra("note", Note::class.java)
            if (note != null) {
                noteListAdapter.addNote(note)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)

        findActivityElements()
        configureRecyclerView()

        setupInsertNoteTextViewListener()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun findActivityElements() {
        notesList = findViewById(R.id.list_notes_recyclerview)
        insertNoteTextView = findViewById(R.id.list_notes_insert_textview)
    }

    private fun configureRecyclerView() {
        noteListAdapter = NoteListAdapter(noteDao.all())
        notesList.layoutManager = LinearLayoutManager(this)
        notesList.adapter = noteListAdapter
    }

    private fun setupInsertNoteTextViewListener() {
        insertNoteTextView.setOnClickListener { _ ->
            val intent = Intent(this, NoteFormActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}