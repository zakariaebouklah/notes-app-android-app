package com.example.note_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    public static final String dbName = "NoteAppDB";
    public static final int dbVersion = 1;

    public static final String USER_TABLE = "User";
    public static final String USER_ID = "_id";
    public static final String USER_USERNAME = "username";
    public static final String USER_EMAIL = "email_address";
    public static final String USER_PASSWORD = "password";

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
                       ", " + USER_USERNAME + " TEXT, " + USER_EMAIL + " TEXT, " + USER_PASSWORD + " TEXT);";
        db.execSQL(query);
    }

    /**
     * this is called whenever the version number of our database changes.
     * it prevents old users of app from breaking when you change the database design.
     * used for forward compatibility or backward compatibility
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + USER_TABLE);
        onCreate(db);
    }

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

    public boolean AddUser(UserModel user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //key, value pair ---> assoc-array
        cv.put(USER_USERNAME, user.getU_username());
        cv.put(USER_EMAIL, user.getU_address());
        cv.put(USER_PASSWORD, user.getU_password());

        //PS: we didn't put a kvp for user ID because it will be auto-generated (auto-incremented)

        //.insert() returns a long : the row ID of the newly inserted row, or -1 if an error occurred
        long rowID = db.insert(USER_TABLE, null, cv);

        return rowID != -1;
    }

}
