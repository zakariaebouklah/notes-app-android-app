package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginPage extends AppCompatActivity {

    Button btn_sign_in;
    EditText et_email_address_login, et_password_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //enabling the back button in the login page...
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_email_address_login = findViewById(R.id.et_email_address_login);
        et_password_login = findViewById(R.id.et_password_login);

        btn_sign_in = findViewById(R.id.btn_sign_in);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}