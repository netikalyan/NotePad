package com.netikalyan.notepad;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class NoteViewModel extends ViewModel {
    private MutableLiveData<Note> selected = new MutableLiveData<Note>();

    public void select(Note value) {
        selected.setValue(value);
    }

    public LiveData<Note> getSelected() {
        return selected;
    }
}
