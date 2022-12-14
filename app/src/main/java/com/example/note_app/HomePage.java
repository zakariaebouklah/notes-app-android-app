package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomePage extends AppCompatActivity{

    private List<NoteModel> notes;
    private NotesAdapter adapter;

    FloatingActionButton btn_new_note;
//    ListView lv_notes;
    private RecyclerView rv_notes;
    private RecyclerView.LayoutManager manager;
    private SearchView search_view;


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

            DatabaseHelper db = new DatabaseHelper(HomePage.this);
            db.LogoutUser();

            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //search view start
        search_view = findViewById(R.id.search_view);
        search_view.clearFocus();
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        //search view end

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

    //start searchview
    private void filterList(String newText) {
        List<NoteModel> filteredList = new ArrayList<>();
        for(NoteModel note : notes){
            if(note.getN_title().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(note);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "no note with this name", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }

    }
    //end searchview
}