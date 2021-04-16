package com.example.olio_ohjelmointi_ht;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LogInTool extends Activity {

    EditText username;
    EditText name;
    EditText age;
    EditText city;
    EditText email;
    EditText password;
    EditText password2;
    String password_text;
    Context context;

    ArrayList<User> user_list = new ArrayList<>();

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.new_user);
        // Finding text boxes from the layout
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        city = (EditText) findViewById(R.id.homeCity);
        email = (EditText) findViewById(R.id.homeCity);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);

        Context context = getApplicationContext();


        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                password_text = s.toString();
                    char [] chars = password_text.toCharArray();
                    for(char c: chars){
                        if(Character.isDigit(c)) {
                            continue;
                        }
                        else{
                            Toast.makeText(context, "Password must contain a number", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
            }

        });

        password2.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if(password2.getText().toString().equals(password_text)){
                    Toast.makeText(context, "Passwords are the same", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Check the password", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
            }

        });
    }

    public void create(View v){
        if(password2.getText().toString().equals(password_text)){
            User user = new User(); // Create a new user and get information from edittexts
            user.setUsername(username.getText().toString());
            user.setName(name.getText().toString());
            user.setAge(Integer.valueOf((age.getText().toString())));
            user.setCity(city.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user_list.add(user); // add user to user_list, using to save multiple users
        }
        else{
            Toast.makeText(context, "Check the password", Toast.LENGTH_SHORT).show();
        }
    }

}
