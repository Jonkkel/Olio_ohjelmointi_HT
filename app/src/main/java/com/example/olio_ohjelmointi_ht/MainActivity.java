package com.example.olio_ohjelmointi_ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText login_username;
    EditText login_password;
    TextView message_box;
    String username;
    String password;
    String message;
    Button sign_up, sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        message_box = (TextView) findViewById(R.id.message_box);

        sign_in = (Button) findViewById(R.id.login_sign_in);
        sign_up = (Button) findViewById(R.id.login_sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) { // set click listener for sign up button--> change layout if pressed
                Intent intent = new Intent(v.getContext(), LogInTool.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void sign_in_button(View v){
        LogInTool LIT = LogInTool.getInstance(this); // get logintool, singleton
        username = login_username.getText().toString(); // read username that user wrote
        password = login_password.getText().toString(); // read password that user wrote

        message = LIT.sign_in(username, password,  v); // give username and password for sign_in method, checks if them are correct and user exists
        message_box.setText(message); // shows the result for user ( wrong password etc.)
        System.out.println(message);
        //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}