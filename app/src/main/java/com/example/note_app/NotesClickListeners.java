package com.example.note_app;

import androidx.cardview.widget.CardView;

public interface NotesClickListeners {

    void onClickingNote(NoteModel note);
    void onLongClickingNote(NoteModel note, CardView cardView);

}
