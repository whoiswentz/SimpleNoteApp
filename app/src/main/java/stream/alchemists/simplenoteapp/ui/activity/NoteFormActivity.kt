package stream.alchemists.simplenoteapp.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import stream.alchemists.simplenoteapp.R
import stream.alchemists.simplenoteapp.dao.NotesDAO
import stream.alchemists.simplenoteapp.models.Note

class NoteFormActivity : AppCompatActivity() {
    private lateinit var title: EditText
    private lateinit var description: EditText
    private var position: Int? = -1
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_form)

        title = findViewById(R.id.form_note_title_edittext)
        description = findViewById(R.id.form_note_description_edittext)

        val note = intent?.getSerializableExtra("note", Note::class.java)
        position = intent?.getIntExtra("position", -1)
        if (note != null && position != -1) {
            title.setText(note.title)
            description.setText(note.description)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note_form_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.note_form_menu_save) {
            val note = Note(title.text.toString(), description.text.toString())
            val intent = Intent()
            intent.putExtra("note", note)
            intent.putExtra("position", position)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}