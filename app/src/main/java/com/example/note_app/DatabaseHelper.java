package com.example.note_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String dbName = "NoteAppDB";

    /**
     * PS: Very Important note: each time we update the database structure (new table for e.g.)
     * we must then increment the database version ; otherwise the db will remain unchangeable
     * thus the new table will not be created.
     */
    public static final int dbVersion = 5;

    //User Table

    public static final String USER_TABLE = "User";
    public static final String USER_ID = "_id";
    public static final String USER_USERNAME = "username";
    public static final String USER_EMAIL = "email_address";
    public static final String USER_PASSWORD = "password";
    public static final String USER_STATUS = "status";

    //Note Table

    public static final String NOTE_TABLE = "Note";
    public static final String NOTE_ID = "n_id";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_BODY = "body";
    public static final String NOTE_CREATION_DATE = "createdAt";
    public static final String NOTE_AUTHOR = "authorId";

    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
        this.context = context;
    }

    /**
     * this method is called the first time a database is accessed.
     * there should be code that creates table(s) of a database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + USER_TABLE + " ( " + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                       ", " + USER_USERNAME + " TEXT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT, " + USER_STATUS + " TEXT);";
        db.execSQL(query);

        String query2 = "create table " + NOTE_TABLE + " (" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + NOTE_TITLE + " TEXT, " + NOTE_BODY + " TEXT, " + NOTE_CREATION_DATE + " TEXT, " + NOTE_AUTHOR + " INTEGER, " +
                        "FOREIGN KEY (" + NOTE_AUTHOR + ") REFERENCES " + USER_TABLE + "(" + USER_ID + "));";
        db.execSQL(query2);
    }

    /**
     * this is called whenever the version number of our database changes.
     * it prevents old users of app from breaking when you change the database design.
     * used for forward compatibility or backward compatibility
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + USER_TABLE);
        db.execSQL("drop table if exists " + NOTE_TABLE);
        onCreate(db);
    }

    /**
     * this method is used to authenticate user into the app.
     * @param userAddress
     * @param userPassword
     * @return
     */

    public boolean AuthUser(String userAddress, String userPassword)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from " + USER_TABLE + " where " + USER_EMAIL + " = ? and " + USER_PASSWORD + " = ?";

        //the rawQuery method Runs the provided SQL and returns a Cursor over the result set.
        Cursor cursor = db.rawQuery(query, new String[] {userAddress, userPassword});
        //Do this before you start doing anything to the cursor. This will make sure the cursor starts at the FIRST row in the database!
        boolean response = cursor.moveToFirst();

        int index = cursor.getColumnIndex(USER_ID);

        Log.d("info", cursor.getString(index));
//        Toast.makeText(context.getApplicationContext(), cursor.getString(index), Toast.LENGTH_SHORT).show();

        cursor.close();

        //the .getCount() method Returns the numbers of rows in the cursor.
        return response;
    }

    /**
     * this method is used to create new user into the app.
     * @param user
     * @return
     */
    public boolean AddUser(UserModel user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //key, value pair ---> assoc-array
        cv.put(USER_USERNAME, user.getU_username());
        cv.put(USER_EMAIL, user.getU_address());
        cv.put(USER_PASSWORD, user.getU_password());
        cv.put(USER_STATUS, user.getU_status());

        //PS: we didn't put a kvp for user ID because it will be auto-generated (auto-incremented)

        //.insert() returns a long : the row ID of the newly inserted row, or -1 if an error occurred
        long rowID = db.insert(USER_TABLE, null, cv);

        return rowID != -1;
    }

    //CRUD operations for Note entity:

    /**
     * this method will add new note into the app. It's the create step in CRUD process.
     * @param note
     * @return rowID of the row inserted in the Note table
     */

    public boolean createNewNote(NoteModel note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTE_TITLE, note.getN_title());
        cv.put(NOTE_BODY, note.getN_body());
        cv.put(NOTE_CREATION_DATE, note.getN_createdAt());
        cv.put(NOTE_AUTHOR, note.getN_authorId());

        long rowID = db.insert(NOTE_TABLE, null, cv);

        Log.d("info", "INSERTED");

        db.close();

        return rowID != -1;
    }

    /**
     * this method will fetch all notes objects in our db. It's the Read part of the CRUD.
     * @return a list of all available Notes in the database.
     */
    public List<NoteModel> getAllNotes()
    {
        List<NoteModel> listOfNotes = new ArrayList<NoteModel>();

        //get an instance of database to read from it:
        SQLiteDatabase db = this.getReadableDatabase();

        //SQL query to get all notes from the Note table.
//        String query = "select * from " + NOTE_TABLE + " order by " + NOTE_CREATION_DATE + " DESC";
        String query = "select * from " + NOTE_TABLE + " inner join " + USER_TABLE +
                       " on " + " Note.authorID = User._id " +
                       " order by " + NOTE_CREATION_DATE + " DESC;";

        //Update: SELECT note.title, note.body, note.created_at, note.authorID FROM `note` JOIN `user` ON note.authorID = user.id WHERE user.id = 3;

        //execute SQL query:
        Cursor cursor = db.rawQuery(query, null);

        /*
         * check if cursor can move to the first element and grab it:
         * if true then loop through all results(Notes) & create new NoteModel object for each record.
         * else make a toast for e.g.
         */

        if (cursor.moveToFirst())
        {
            do {
                NoteModel note = new NoteModel(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                );

                //add each completed note to the list that will be displayed in the home page.

                listOfNotes.add(note);

            } while(cursor.moveToNext());
        }

        //Remember to close the cursor & the db each time we do a fetch from db.
        cursor.close();
        db.close();

        return listOfNotes;
    }

    /**
     * this method delete a note from our db. It's the Delete part of the CRUD.
     * @param noteSelected
     */
    public void deleteNote(NoteModel noteSelected)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTE_TABLE, "" + NOTE_CREATION_DATE + " = ? ", new String[]{ noteSelected.getN_createdAt() });
    }

    /**
     * this method delete a note from our db. It's the Update part of the CRUD.
     * @param note
     * @return
     */
    public boolean updateNote(NoteModel note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTE_TITLE, note.getN_title());
        cv.put(NOTE_BODY, note.getN_body());
        cv.put(NOTE_CREATION_DATE, getDateTime());

        int response = db.update(NOTE_TABLE, cv, "" + NOTE_CREATION_DATE + " = ? " , new String[]{note.getN_createdAt()});

        db.close();

        return response == -1;

    }

    public String getDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(

                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        dateFormat.setTimeZone(TimeZone.getTimeZone("Africa/Casablanca"));

        Date date = new Date();

        return dateFormat.format(date);
    }

    @SuppressLint("Range")
    public String getCurrentUserID()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(USER_TABLE, new String[]{USER_ID}, USER_STATUS + " = ? ", new String[]{"1"}, null, null ,null);
        cursor.moveToFirst();

        return cursor.getString(0);
    }

}
