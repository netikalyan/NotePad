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
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.netikalyan.notepad.R;
import com.netikalyan.notepad.db.Note;
import com.netikalyan.notepad.ui.list.NoteViewModel;

public class EditFragment extends Fragment {

    private static final String TAG = "NotesEditFragment";
    private EditViewModel mEditViewModel;
    private EditText mNoteText;
    private EditText mNoteTitle;
    private boolean mIsNewNote;
    private boolean mIsTitleEmpty;
    private boolean mIsContentEmpty;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View mainView = inflater.inflate(R.layout.edit_fragment, container, false);
        mNoteText = mainView.findViewById(R.id.noteViewer);

        mNoteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditViewModel.getSelected().getValue().setContent(s.toString());
                if (s.length() == 0) {
                    mIsContentEmpty = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEditViewModel.getSelected().getValue().setContent(s.toString());
            }
        });
        mNoteTitle = mainView.findViewById(R.id.noteTitle);
        mNoteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditViewModel.getSelected().getValue().setTitle(s.toString());
                if (s.length() == 0) {
                    mIsTitleEmpty = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEditViewModel.getSelected().getValue().setTitle(s.toString());
            }
        });

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
        if (mIsNewNote)
            mEditViewModel.select(new Note());
        mEditViewModel.getSelected().observe(this, note -> {
            if (null != note.getTitle()) {
                Log.d(TAG, "Title: " + note.getTitle());
                mNoteTitle.setText(note.getTitle());
            }
            if (null != note.getContent()) {
                Log.d(TAG, "Content: " + note.getContent());
                mNoteText.setText(note.getContent());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null)
            menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_save) {
            saveNote();
        }
        if (item.getItemId() == R.id.action_menu_delete) {
            deleteNote();
        }
        if (item.getItemId() == R.id.action_menu_archive) {
            archiveNote();
        }
        if (item.getItemId() == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mEditViewModel.getSelected().getValue().getContent());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_notes));
            startActivity(shareIntent);
        }
        if (item.getItemId() == R.id.nav_send) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setDataAndType(Uri.parse("mailto:"), "plaint/text");
            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{});
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, mEditViewModel.getSelected().getValue().getTitle());
            sendIntent.putExtra(Intent.EXTRA_TEXT, mEditViewModel.getSelected().getValue().getContent());

            Intent shareIntent = Intent.createChooser(sendIntent, getString(R.string.send_mail));
            startActivity(shareIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void archiveNote() {
        Log.d(TAG, "archiveNote");
        NoteViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);
        mViewModel.archive(mEditViewModel.getSelected().getValue());
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void deleteNote() {
        Log.d(TAG, "deleteNote");
        NoteViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);
        mViewModel.delete(mEditViewModel.getSelected().getValue());
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void saveNote() {
        Log.d(TAG, "saveNote : " + mIsNewNote);
        NoteViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);
        if (mIsNewNote) {
            mViewModel.insert(mEditViewModel.getSelected().getValue());
            mIsNewNote = false;
        } else {
            mViewModel.update(mEditViewModel.getSelected().getValue());
        }
    }
}
