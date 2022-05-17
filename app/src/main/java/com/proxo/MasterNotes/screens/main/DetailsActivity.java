package com.proxo.MasterNotes.screens.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.proxo.MasterNotes.Main;
import com.proxo.MasterNotes.R;
import com.proxo.MasterNotes.model.Note;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class DetailsActivity extends AppCompatActivity {

    private Note note;
    private EditText editText;
    private static final String EXTRA_NOTE = "DetailsActivity.EXTRA_NOTE";

    public static void start(Activity caller, Note note) {
        Intent intent = new Intent(caller, DetailsActivity.class);
        if (note != null)
            intent.putExtra(EXTRA_NOTE, note);
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle(getString(R.string.note_details_title));
        editText = findViewById(R.id.text);
        if (getIntent().hasExtra(EXTRA_NOTE))
        {
            note = getIntent().getParcelableExtra(EXTRA_NOTE);
            editText.setText(note.text);
        }
        else
            note = new Note();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                if (editText.getText().length() > 0 && !editText.getText().toString().trim().isEmpty())
                {
                    note.text = editText.getText().toString();
                    note.done = false;
                    note.timestamp = System.currentTimeMillis();
                    if (getIntent().hasExtra(EXTRA_NOTE))
                        Main.getInstance().getNoteDao().update(note);
                    else
                        Main.getInstance().getNoteDao().insert(note);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
