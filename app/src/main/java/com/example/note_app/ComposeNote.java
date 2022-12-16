package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class ComposeNote extends AppCompatActivity {

    Button btn_done;
    EditText et_note_title, et_note_body;

    Boolean editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        //enabling the back button in the new note page.
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        btn_done = findViewById(R.id.btn_done);

        et_note_title = findViewById(R.id.et_note_title);
        et_note_body = findViewById(R.id.et_note_body);

        if(getIntent().getExtras() != null)
        {
            et_note_title.setText(getIntent().getExtras().getString("TITLE"));
            et_note_body.setText(getIntent().getExtras().getString("BODY"));
        }

        if (et_note_title.getText().toString().equals("") && et_note_body.getText().toString().equals(""))
        {
            editing = false;
        }
        else {
            editing = true;
        }

        btn_done.setOnClickListener(new View.OnClickListener() {

            /**
             * this function is used to capture the DateTime of creation in the moment when we click on "DONE" button
             * the DateTime is formatted so that it can be stored as String in Database
             * @return
             */
            public String getDateTime()
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat(

                        "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                dateFormat.setTimeZone(TimeZone.getTimeZone("Africa/Casablanca"));

                Date date = new Date();

                return dateFormat.format(date);
            }


            /**
             * This function describes next step after either success creation of note or failure:
             * @param db
             * @param note
             * @param editing
             */
            private void nextBehaviour(DatabaseHelper db, NoteModel note, Boolean editing) {
                if (!editing)
                {
                    boolean result = db.createNewNote(note);
                    if (result)
                    {
                        Intent intent = new Intent(ComposeNote.this, HomePage.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(ComposeNote.this, "An Error Occurred somewhere...", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    boolean result = db.updateNote(new NoteModel(
                            et_note_title.getText().toString(),
                            et_note_body.getText().toString(),
                            getIntent().getExtras().getString("DATE"),
                            Integer.parseInt(db.getCurrentUserID())
                    ));
                    Log.d("info", "result : " + result);
                    if (!result)
                    {
                        Intent intent = new Intent(ComposeNote.this, HomePage.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(ComposeNote.this, "An Error Occurred somewhere...", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            //The Click Handler:
            @Override
            public void onClick(View view) {

                DatabaseHelper db = new DatabaseHelper(ComposeNote.this);

                NoteModel note;

                if (!et_note_body.getText().toString().equals("") && !et_note_title.getText().toString().equals(""))
                {
                    note = new NoteModel(
                                et_note_title.getText().toString(),
                                et_note_body.getText().toString(),
                                getDateTime(),
                                Integer.parseInt(db.getCurrentUserID())
                            );

                    nextBehaviour(db, note, editing);
                }
                else
                {
                    if(!et_note_body.getText().toString().equals("") && et_note_title.getText().toString().equals(""))
                    {
                        note = new NoteModel(
                                "Untitled",
                                et_note_body.getText().toString(),
                                getDateTime(),
                                Integer.parseInt(db.getCurrentUserID())
                        );

                        nextBehaviour(db, note, editing);
                    }
                    else if(et_note_body.getText().toString().equals("") && !et_note_title.getText().toString().equals(""))
                    {
                        note = new NoteModel(
                                et_note_title.getText().toString(),
                                "No additional text",
                                getDateTime(),
                                Integer.parseInt(db.getCurrentUserID())
                        );

                        nextBehaviour(db, note, editing);
                    }
                    else
                    {
                        note = new NoteModel(
                                "Untitled",
                                "No additional text",
                                getDateTime(),
                                Integer.parseInt(db.getCurrentUserID())
                        );

                        nextBehaviour(db, note, editing);
                    }
                }

                //Display(Update) All Notes in database when the "compose new note" button is clicked:

                RecyclerView rv_notes = findViewById(R.id.rv_notes);

                //we use this constructor : ArrayAdapter(Context context, int resource, List<T> objects)

//                ArrayAdapter<NoteModel> arr = new ArrayAdapter<NoteModel>(
//                        getApplicationContext(),
//                        android.R.layout.simple_expandable_list_item_1,
//                        db.getAllNotes()
//                );
//                lv_notes.setAdapter(arr);

//                Log.d("info", "arr : " + arr.toString());

                NotesAdapter adapter = new NotesAdapter(getApplicationContext(), db.getAllNotes());
//                rv_notes.setAdapter(adapter);
            }
        });

    }
}