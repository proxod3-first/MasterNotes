package com.proxo.MasterNotes.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.proxo.MasterNotes.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase
{
    public abstract NoteDao noteDao();
}
