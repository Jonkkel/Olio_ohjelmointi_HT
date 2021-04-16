package com.example.olio_ohjelmointi_ht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button sign_up = (Button) findViewById(R.id.login_sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // set click listener for sign up button--> change layout if pressed
                Intent intent = new Intent(v.getContext(), LogInTool.class);
                startActivityForResult(intent, 0);
            }
        });


    }
}

