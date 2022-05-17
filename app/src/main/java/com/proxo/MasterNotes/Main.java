package com.proxo.MasterNotes;

import android.app.Application;

import androidx.room.Room;

import com.proxo.MasterNotes.data.NotesDatabase;
import com.proxo.MasterNotes.data.NoteDao;

public class Main extends Application {

    private NotesDatabase database;
    private NoteDao noteDao;
    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(),
                NotesDatabase.class, "app-db-name")
                .allowMainThreadQueries()
                .build();

        noteDao = database.noteDao();
    }

    public NotesDatabase getDatabase() {
        return database;
    }
    public NoteDao getNoteDao() {
        return noteDao;
    }
    public void setDatabase(NotesDatabase database) {
        this.database = database;
    }
    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }
}
