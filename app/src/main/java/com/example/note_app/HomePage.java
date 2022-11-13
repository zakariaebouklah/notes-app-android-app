package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePage extends AppCompatActivity {

    FloatingActionButton btn_new_note;
//    ListView lv_notes;
    private RecyclerView rv_notes;
    private RecyclerView.ViewHolder viewHolder;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btn_new_note = findViewById(R.id.btn_new_note);

        btn_new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, NewNote.class);
                startActivity(i);
            }
        });

        //Display All Notes in database:

        DatabaseHelper db = new DatabaseHelper(HomePage.this);

        /*
         * Old way of creating a classic recycle view list layout.
         */

//        lv_notes = findViewById(R.id.lv_notes);

        //we use this constructor : ArrayAdapter(Context context, int resource, List<T> objects)

//        ArrayAdapter<NoteModel> arr = new ArrayAdapter<NoteModel>(
//                HomePage.this,
//                android.R.layout.simple_expandable_list_item_1,
//                db.getAllNotes()
//        );
//        lv_notes.setAdapter(arr);

        /*
         * switching to adapter class to create a cool layout for our list.
         */

        rv_notes = findViewById(R.id.rv_notes);

        //this setting is used to gain some performance.
        rv_notes.setHasFixedSize(true);

        //use a linear layoutManager.
        manager = new LinearLayoutManager(HomePage.this);
        rv_notes.setLayoutManager(manager);

        //specifying an adapter for the recyclerView.
        NotesAdapter adapter = new NotesAdapter(HomePage.this, db.getAllNotes());
        rv_notes.setAdapter(adapter);
    }
}