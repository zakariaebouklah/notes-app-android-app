package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationPage extends AppCompatActivity {

    Button btn_sign_up;
    EditText et_email_address_register,
            et_password_register, et_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_email_address_register = findViewById(R.id.et_email_address_register);
        et_password_register = findViewById(R.id.et_password_register);
        et_username = findViewById(R.id.et_username);

        btn_sign_up = findViewById(R.id.btn_sign_up);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserModel user;

                try {
                    user = new UserModel(et_email_address_register.getText().toString(),
                                                    et_password_register.getText().toString(),
                                                    et_username.getText().toString());

                    Toast.makeText(RegistrationPage.this, user.toString(), Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(RegistrationPage.this, "Registration Failed...", Toast.LENGTH_LONG).show();
                    user = new UserModel("error@error.com",
                            "error1234567",
                            "ErrorUser");
                }

                DatabaseHelper dbHelper = new DatabaseHelper(RegistrationPage.this);

                boolean result = dbHelper.AddUser(user);

                if (result) Toast.makeText(RegistrationPage.this, "Successful registration", Toast.LENGTH_LONG).show();
                else Toast.makeText(RegistrationPage.this, "Successful registration", Toast.LENGTH_LONG).show();
            }
        });
    }
}