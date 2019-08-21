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

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.netikalyan.notepad.R;
import com.netikalyan.notepad.db.Note;
import com.netikalyan.notepad.ui.list.NoteViewModel;

public class EditFragment extends Fragment {

    private static final String TAG = "NotesEditFragment";
    private EditViewModel mEditViewModel;
    private NoteViewModel mViewModel;
    private EditText mNoteText;
    private boolean mIsNewNote;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View mainView = inflater.inflate(R.layout.edit_fragment, container, false);
        mNoteText = mainView.findViewById(R.id.noteViewer);
        FloatingActionButton fab = mainView.findViewById(R.id.fabSave);
        fab.setOnClickListener(view -> saveNote());
        Bundle bundle = getArguments();
        if (null != bundle) {
            mIsNewNote = bundle.getBoolean("NEW_NOTE");
        }
        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        mEditViewModel = ViewModelProviders.of(getActivity()).get(EditViewModel.class);
        mViewModel = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);
        if (mIsNewNote)
            mEditViewModel.select(new Note());
        mEditViewModel.getSelected().observe(this, note -> mNoteText.setText(note.getContent()));
    }

    private void saveNote() {
        Log.d(TAG, "saveNote");
        //Objects.requireNonNull(mEditViewModel.getSelected().getValue()).setContent(mNoteText.getText().toString());
        mViewModel.insert(mEditViewModel.getSelected().getValue());
    }
}
