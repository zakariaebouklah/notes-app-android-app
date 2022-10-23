package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

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