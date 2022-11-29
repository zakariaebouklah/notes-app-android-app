package com.example.note_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> implements PopupMenu.OnMenuItemClickListener{

    private Context context;
    private RecyclerView rv_notes;
    private List<NoteModel> notesList;
    private NoteModel noteSelected;
    private NotesClickListeners listeners = new NotesClickListeners() {
        @Override
        public void onClickingNote(NoteModel note) {
            Toast.makeText(context, "Clicked...", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, ComposeNote.class);
            i.putExtra("TITLE", note.getN_title());
            i.putExtra("BODY", note.getN_body());
            i.putExtra("DATE", note.getN_createdAt());
            context.startActivity(i);
        }

        @Override
        public void onLongClickingNote(NoteModel note, CardView cardView) {
            Toast.makeText(context, "Long Clicked...", Toast.LENGTH_SHORT).show();
            noteSelected = note;
            showTheDialog(cardView);
        }
    };

    private void showTheDialog(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(context, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_dialog);
        popupMenu.show();
    }

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

        //Generate a New color randomly foreach new card created:

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.cv_notes.setCardBackgroundColor(color);

        //setting click listener on the cardView (the note) :
        holder.cv_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listeners.onClickingNote(notesList.get(holder.getAdapterPosition()));
            }
        });

        //setting the longClick listener on the cardView .
        holder.cv_notes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listeners.onLongClickingNote(notesList.get(holder.getAdapterPosition()), holder.cv_notes);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        DatabaseHelper db = new DatabaseHelper(context);
        switch (menuItem.getItemId())
        {
            case R.id.delete:
                db.deleteNote(noteSelected);
                notesList.remove(noteSelected);
                //reloading the HomePage Activity:
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
                return true;
        }

        return false;
    }

    private void updateRecyclerView(List<NoteModel> notes) {
        rv_notes = rv_notes.findViewById(R.id.rv_notes);
        rv_notes.setHasFixedSize(true);
//        rv_notes.setLayoutManager();
        context.startActivity(new Intent(context.getApplicationContext(), HomePage.class));
//        NotesAdapter notesAdapter = new NotesAdapter(context, notes);
//        rv_notes.setAdapter(notesAdapter);
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView tv_titleOfNote, tv_dateOfCreation;
        CardView cv_notes;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_titleOfNote = itemView.findViewById(R.id.tv_titleOfNote);
            tv_dateOfCreation = itemView.findViewById(R.id.tv_dateOfCreation);
            cv_notes = itemView.findViewById(R.id.cv_notes);
        }
    }
}
