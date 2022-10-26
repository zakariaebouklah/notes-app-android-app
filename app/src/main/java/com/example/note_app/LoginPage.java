package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                DatabaseHelper db = new DatabaseHelper(LoginPage.this);

                try {
                    boolean res_auth = db.AuthUser(
                            et_email_address_login.getText().toString(),
                            et_password_login.getText().toString()
                    );

                    if (res_auth)
                    {
                        Toast.makeText(LoginPage.this, "You're Logged In...", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginPage.this, HomePage.class);
                        startActivity(i);
                    }
                    else Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();


                }
                catch (Exception e)
                {
                    Toast.makeText(LoginPage.this, "Login FAILED...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}