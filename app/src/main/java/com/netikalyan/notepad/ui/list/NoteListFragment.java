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

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.netikalyan.notepad.OnNoteSelectedListener;
import com.netikalyan.notepad.R;

public class NoteListFragment extends Fragment {

    public static final String NOTES_TYPE = "NOTES_TYPE";
    public static final int ALL_NOTES = 1;
    public static final int ARCHIVED_NOTES = 2;
    public static final int DELETED_NOTES = 3;
    private static final String TAG = "NotesListFragment";
    private static final String PREF_LAYOUT = "pref_layout";
    private static final int GRID_LAYOUT = 1;
    private static final int LINEAR_LAYOUT = 2;
    private OnNoteSelectedListener mListener;
    private NoteListAdapter mListAdapter;
    private RecyclerView mRecyclerView;
    private SharedPreferences mPreferences;
    private int mNotesType = ALL_NOTES;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        try {
            mListener = (OnNoteSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNoteSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Bundle bundle = getArguments();
        if (null != bundle) {
            mNotesType = bundle.getInt(NOTES_TYPE, ALL_NOTES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        setHasOptionsMenu(true);
        View mainView = inflater.inflate(R.layout.content_main, container, false);
        mRecyclerView = mainView.findViewById(R.id.notesRecyclerView);
        mListAdapter = new NoteListAdapter(getActivity(), mListener);
        mRecyclerView.setAdapter(mListAdapter);

        FloatingActionButton fab = mainView.findViewById(R.id.fabNew);
        fab.setOnClickListener(view -> mListener.onNoteSelected(true));

        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        NoteViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);
        switch (mNotesType) {
            case ALL_NOTES:
                mViewModel.getAllNotes().observe(getActivity(), notes -> mListAdapter.setNotes(notes));
                break;
            case ARCHIVED_NOTES:
                mViewModel.getArchivedNotes().observe(getActivity(), notes -> mListAdapter.setNotes(notes));
                break;
            case DELETED_NOTES:
                mViewModel.getDeletedNotes().observe(getActivity(), notes -> mListAdapter.setNotes(notes));
                break;
            default:
                break;
        }
        mPreferences = getActivity().getApplicationContext().getSharedPreferences(NoteListFragment.class.getCanonicalName(), Context.MODE_PRIVATE);
        setLayoutManager(mPreferences.getInt(PREF_LAYOUT, LINEAR_LAYOUT));
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
        inflater.inflate(R.menu.menu_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_menu_grid) {
            setLayoutManager(GRID_LAYOUT);
        }
        if (item.getItemId() == R.id.action_menu_linear) {
            setLayoutManager(LINEAR_LAYOUT);
        }
        return super.onOptionsItemSelected(item);
    }


    private void setLayoutManager(int layoutType) {
        if (layoutType == GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else if (layoutType == LINEAR_LAYOUT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            assert false;
        }
        mPreferences.edit().putInt(PREF_LAYOUT, layoutType).apply();
    }
}
