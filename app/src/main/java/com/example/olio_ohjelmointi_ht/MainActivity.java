package com.example.olio_ohjelmointi_ht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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

import java.io.IOException;
import java.util.Locale;


public class MainActivity extends AppCompatActivity{

    EditText login_username;
    EditText login_password;
    TextView message_box;
    String username;
    String password;
    String message;
    AppCompatButton sign_up, sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingTool set = SettingTool.getInstance(this);
        set.loadLocale();
        setContentView(R.layout.activity_main);
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        message_box = (TextView) findViewById(R.id.message_box);

        System.out.println(this.getFilesDir());
        sign_in = (AppCompatButton) findViewById(R.id.login_sign_in);
        sign_up = (AppCompatButton) findViewById(R.id.login_sign_up);

        sign_up.setOnClickListener(v -> { // set click listener for sign up button--> change layout if pressed
            //Intent intent = new Intent(v.getContext(), LogInTool.class);
            //startActivityForResult(intent, 0);
            Fragment fragment;
            // If any button has been pressed then lets set it back to enabled
            fragment = new New_User();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.New_user_fragment, fragment);
            transaction.commit();
        });
    }

    public void sign_in_button(View v) throws IOException {

        LogInTool LIT = LogInTool.getInstance(this); // get logintool, singleton
        username = login_username.getText().toString(); // read username that user wrote
        password = login_password.getText().toString(); // read password that user wrote

        message = LIT.sign_in2(username, password); // give username and password for sign_in method, checks if them are correct and user exists
        message_box.setText(message); // shows the result for user ( wrong password etc.)
        System.out.println(message);
        if (message.equals(getString(R.string.welcome))){
            Intent intent = new Intent(this, Begin.class);
            startActivityForResult(intent, 0);
            finish();
        }
        //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        //
    }

}
