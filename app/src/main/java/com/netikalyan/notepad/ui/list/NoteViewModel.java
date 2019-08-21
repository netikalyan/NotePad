/*
 * MIT License
 *
 * Copyright (c) 2019 netikalyan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.netikalyan.notepad.ui.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.netikalyan.notepad.db.Note;
import com.netikalyan.notepad.db.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private static final String TAG = "NotesViewModel";
    private final NoteRepository mRepository;
    private final LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "NoteViewModel");
        mRepository = new NoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        Log.d(TAG, "getAllNotes");
        return mAllNotes;
    }

    public void insert(Note note) {
        Log.d(TAG, "insert");
        mRepository.insert(note);
    }

    public void delete(Note note) {
        Log.d(TAG, "delete");
        mRepository.delete(note);
    }

    public void update(Note note) {
        Log.d(TAG, "update");
        mRepository.update(note);
    }

    public void deleteAll() {
        Log.d(TAG, "deleteAll");
        mRepository.deleteAll();
    }
}
