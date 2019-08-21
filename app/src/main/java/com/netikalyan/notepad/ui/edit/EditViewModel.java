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

package com.netikalyan.notepad.ui.edit;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.netikalyan.notepad.db.Note;

public class EditViewModel extends ViewModel {

    private static final String TAG = "NotesEditViewModel";
    private final MutableLiveData<Note> selected = new MutableLiveData<>();

    public void select(Note value) {
        Log.d(TAG, "select. Note Content = " + value.getContent());
        selected.setValue(value);
    }

    public LiveData<Note> getSelected() {
        Log.d(TAG, "getSelected");
        if (selected.getValue() != null) {
            Log.d(TAG, "Note Content = " + selected.getValue().getContent());
        }
        return selected;
    }
}
