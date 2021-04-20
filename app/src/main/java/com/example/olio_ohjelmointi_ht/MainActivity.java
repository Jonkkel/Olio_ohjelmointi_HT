package com.example.olio_ohjelmointi_ht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    EditText login_username;
    EditText login_password;
    TextView message_box;
    String username;
    String password;
    String message;
    Button sign_up, sign_in;
    MenuItem lastItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingTool set = SettingTool.getInstance(this);
        set.loadLocale();
        setContentView(R.layout.activity_main);
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        message_box = (TextView) findViewById(R.id.message_box);

        sign_in = (Button) findViewById(R.id.login_sign_in);
        sign_up = (Button) findViewById(R.id.login_sign_up);

        /*
        Android studio ehdotti että vaihda
        TÄMÄ:
            sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) { // set click listener for sign up button--> change layout if pressed
                    Intent intent = new Intent(v.getContext(), LogInTool.class);
                    startActivityForResult(intent, 0);
                }
            });

         Tällaiseen*/
        sign_up.setOnClickListener(v -> { // set click listener for sign up button--> change layout if pressed
            Intent intent = new Intent(v.getContext(), LogInTool.class);
            startActivityForResult(intent, 0);
        });
    }

    public void sign_in_button(View v) {
        // Ei jaksanut aina kirjautua ni nyt pääsee eteenpäin kun vaan painaa tuota sign in :D
        Intent intent = new Intent(v.getContext(), Begin.class);
        startActivityForResult(intent, 0);

        /* ELÄ POISTA TÄTÄ!!!
        LogInTool LIT = LogInTool.getInstance(this); // get logintool, singleton
        username = login_username.getText().toString(); // read username that user wrote
        password = login_password.getText().toString(); // read password that user wrote

        message = LIT.sign_in(username, password,  v); // give username and password for sign_in method, checks if them are correct and user exists
        message_box.setText(message); // shows the result for user ( wrong password etc.)
        System.out.println(message);
        //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        */
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        // If any button has been pressed then lets set it back to enabled
        if (lastItem != null) {
            lastItem.setEnabled(true);
        }
        if (item.getItemId() == R.id.login_sign_up) {
            fragment = new New_User();
        }
        else{
                return false;
            }
            lastItem = item;
            item.setEnabled(false);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.New_user_fragment, fragment);
            transaction.commit();
            return true;
        }
    }
