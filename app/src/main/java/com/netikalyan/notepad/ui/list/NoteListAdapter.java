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
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netikalyan.notepad.OnNoteSelectedListener;
import com.netikalyan.notepad.R;
import com.netikalyan.notepad.db.Note;
import com.netikalyan.notepad.ui.edit.EditViewModel;

import java.util.List;

class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private static final String TAG = "NotesListAdapter";
    private List<Note> mNotes;
    private final OnNoteSelectedListener mListener;
    private final EditViewModel mEditModel;

    public NoteListAdapter(FragmentActivity fragment, OnNoteSelectedListener listener) {
        Log.d(TAG, "NoteListAdapter");
        this.mListener = listener;
        mEditModel = ViewModelProviders.of(fragment).get(EditViewModel.class);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mInfoText;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mInfoText = itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.d(TAG, "onItemClick [" + position + "]");
            mEditModel.select(mNotes.get(position));
            mListener.onNoteSelected(false);
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_content_drawer, viewGroup, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder viewHolder, int position) {
        if (mNotes != null) {
            Note current = mNotes.get(position);
            if (current.getTitle() != null && !current.getTitle().isEmpty()) {
                viewHolder.mInfoText.setText(current.getTitle());
            } else {
                viewHolder.mInfoText.setText("No Title");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null) {
            Log.d(TAG, "getItemCount [" + mNotes.size() + "]");
            return mNotes.size();
        } else {
            Log.d(TAG, "getItemCount [0]");
            return 0;
        }
    }

    void setNotes(List<Note> notes) {

        Log.d(TAG, "setNotes[" + notes.size() + "]");
        mNotes = notes;
        notifyDataSetChanged();
    }
}
