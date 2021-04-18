package com.example.olio_ohjelmointi_ht;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInTool extends Activity {

    EditText username;
    EditText name;
    EditText age;
    EditText city;
    EditText email;
    EditText password;
    EditText password2;
    String password_text;
    TextView password_requirements;
    TextView password_match;
    TextView message;
    String log_in_message;
    String result;
    Context context;

    ArrayList<User> user_list = new ArrayList<>();

    private static LogInTool LIT = null; // singleton

    public static LogInTool getInstance() {
        if (LIT == null) {
            LIT = new LogInTool();
        }
        return LIT; // return only one and same LogInTool
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.new_user);
        // Finding text boxes etc. from the layout
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        city = (EditText) findViewById(R.id.homeCity);
        email = (EditText) findViewById(R.id.homeCity);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        password_requirements = (TextView) findViewById(R.id.requirements);
        password_match = (TextView) findViewById(R.id.password_match);
        message = (TextView) findViewById(R.id.message);

        context = getApplicationContext(); // context, in case of need


        password.addTextChangedListener(new TextWatcher() { // Setting textWatcher for password box, we can tell user if the password is good
            @Override
            public void afterTextChanged(Editable s) {
                password_text = s.toString();
                int number = 0;
                int upper = 0;
                int lower = 0;
                if(password.getText().toString().length() >= 8){
                    for(int i = 0; i < password_text.length(); i++){
                        char c = password_text.charAt(i);
                        if(Character.isUpperCase(c)){
                            upper++;
                        }
                        if(Character.isLowerCase(c)){
                            lower++;
                        }
                        if(Character.isDigit(c)){
                            number++;
                        }
                        if (upper > 0 && lower > 0 && number > 0){
                            System.out.println("Kaikki hyvin");
                            password_requirements.setText("");
                            break;
                        }
                        if(upper == 0){
                            System.out.println("ISOJA");
                            password_requirements.setText(getResources().getString(R.string.contain_upper));
                        }
                        if(lower == 0){
                            System.out.println("PIENIÄ");
                            password_requirements.setText(getResources().getString(R.string.contain_lower));
                        }
                        if(number == 0){
                            System.out.println("NUMEROITA");
                            password_requirements.setText(getResources().getString(R.string.contain_numbers));
                        }
                    }
                }
                else{
                    password_requirements.setText(getResources().getString(R.string.password_length));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
            }

        });

        password2.addTextChangedListener(new TextWatcher() { // Setting textWatcher for password again box, checking if the passwords are same

            @Override
            public void afterTextChanged(Editable s) {
                if (password2.getText().toString().equals(password_text)) {
                    password_match.setText(getResources().getString(R.string.password_correct));
                } else {
                    password_match.setText(getResources().getString(R.string.password_incorrect));
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

    public void create_button(View v) { // adds user to user_list, creates user
        if (password2.getText().toString().equals(password_text) && password_text != "" && username.getText().toString() != "" && name.getText().toString() != "" && age.getText().toString() != "" && city.getText().toString() != "" && email.getText().toString() != "") {
            User user = new User(); // Create a new user and get information from edittexts
            user.setUsername(username.getText().toString());
            user.setName(name.getText().toString());
            user.setAge(Integer.valueOf((age.getText().toString())));
            user.setCity(city.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user_list.add(user); // add user to user_list, using to save multiple users
            log_in_message = sign_in(username.getText().toString(), password.getText().toString(), v);
            message.setText(log_in_message);
        } else {
            password_match.setText(getResources().getString(R.string.password_incorrect));
        }
    }

    public String sign_in(String username, String password, View v) { // let user sign in and use the app
        if(user_list.size() == 0){
            System.out.println("testi1");
            result = ("No user found on this username");
        }
        for (int i = 0; i < user_list.size(); i++) {
            if (user_list.get(i).getUsername().equals(username)) {
                if (user_list.get(i).getPassword().equals(password)) {
                    result = ("Password correct, welcome!");
                    Intent intent = new Intent(v.getContext(), Begin_screen.class);
                    startActivityForResult(intent, 0);
                }
                else {
                    System.out.println("testi2");
                    result = ("Wrong password, if you have forgotten your password, press forgot your password");
                }
            } else {
                System.out.println("testi3");
                result = ("No user found on this username");
            }
        }
        return result;
    }
}
