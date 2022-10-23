package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //enabling the back button in the login page...
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}