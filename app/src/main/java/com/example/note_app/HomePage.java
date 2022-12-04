package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomePage extends AppCompatActivity{

    private List<NoteModel> notes;
    private NotesAdapter adapter;

    FloatingActionButton btn_new_note;
//    ListView lv_notes;
    private RecyclerView rv_notes;
    private RecyclerView.LayoutManager manager;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logout, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.logout)
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btn_new_note = findViewById(R.id.btn_new_note);

        btn_new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this, ComposeNote.class);
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
        notes = db.getAllNotes();
        rv_notes = findViewById(R.id.rv_notes);

        //this setting is used to gain some performance.
        rv_notes.setHasFixedSize(true);

        //use a linear layoutManager.
//        manager = new LinearLayoutManager(HomePage.this);
        //Update: use a Staggered-grid layoutManager
        manager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_notes.setLayoutManager(manager);

        //specifying an adapter for the recyclerView.
        adapter = new NotesAdapter(HomePage.this, notes);
        rv_notes.setAdapter(adapter);

    }
}