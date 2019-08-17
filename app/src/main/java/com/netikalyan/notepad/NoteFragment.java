package com.netikalyan.notepad;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class NoteFragment extends Fragment {

    private NoteViewModel mViewModel;
    private EditText mNoteText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);
        mViewModel.getSelected().observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                mNoteText.setText(note.getText());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_content, container, false);
        mNoteText = view.findViewById(R.id.noteViewer);
        return view;
    }
}
