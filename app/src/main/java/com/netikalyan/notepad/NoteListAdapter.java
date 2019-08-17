package com.netikalyan.notepad;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView mInfoText;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mInfoText = itemView.findViewById(R.id.info_text);
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
        viewHolder.mInfoText.setText("Position " + position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
