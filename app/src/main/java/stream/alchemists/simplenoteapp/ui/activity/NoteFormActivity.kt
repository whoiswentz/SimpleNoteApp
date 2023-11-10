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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_form)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note_form_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.note_form_menu_save) {
            val title: EditText = findViewById(R.id.form_note_title_edittext)
            val description: EditText = findViewById(R.id.form_note_description_edittext)
            val note = Note(title.text.toString(), description.text.toString())
            val intent = Intent()
            intent.putExtra("note", note)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}