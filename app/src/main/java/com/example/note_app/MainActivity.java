package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_login, btn_register;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Boolean nightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        //here we're saving the default preferences of the user even when he quits the app.
        preferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);

        //light mode is the default mode for this app.
        nightMode = preferences.getBoolean("night", false);
    }

    //the following overrode method append the Menu (the menu called menu_switch_theme) and its items in the Action Bar.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_switch_theme, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.switcher) {
            if(nightMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor = preferences.edit();
                editor.putBoolean("night", false);
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor = preferences.edit();
                editor.putBoolean("night", true);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);

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