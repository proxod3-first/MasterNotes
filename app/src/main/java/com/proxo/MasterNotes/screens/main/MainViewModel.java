package com.proxo.MasterNotes.screens.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.proxo.MasterNotes.Main;
import com.proxo.MasterNotes.model.Note;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final LiveData<List<Note>> noteLiveData = Main.getInstance().getNoteDao().getAllLiveData();
    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }
}
