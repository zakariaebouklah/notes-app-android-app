package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchLogin(View v)
    {
        Intent i = new Intent(this, LoginPage.class);
        startActivity(i);
    }

    public void launchRegistration(View v)
    {
        Intent i = new Intent(this, RegistrationPage.class);
        startActivity(i);
    }
}