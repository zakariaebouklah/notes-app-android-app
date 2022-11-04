package com.example.note_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private Context context;
    private List<NoteModel> notesList;

    public NotesAdapter(Context context, List<NoteModel> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_ui, parent, false);
        //associating layout with a holder.
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {
        holder.tv_titleOfNote.setText(notesList.get(position).getN_title());
        holder.tv_dateOfCreation.setText(notesList.get(position).getN_createdAt());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView tv_titleOfNote, tv_dateOfCreation;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_titleOfNote = itemView.findViewById(R.id.tv_titleOfNote);
            tv_dateOfCreation = itemView.findViewById(R.id.tv_dateOfCreation);
        }
    }
}
