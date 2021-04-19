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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    Context c;

    public ArrayList<User> user_list = new ArrayList<>(); // list for multiple users

    private static LogInTool LIT = null; // singleton

    public static LogInTool getInstance(Context con) {
        if (LIT == null) {
            LIT = new LogInTool();
            LIT.LoginTool(con);
        }
        return LIT; // return only one and same LogInTool
    }

    public void LoginTool(Context con){
        this.c = con;
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
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        password_requirements = (TextView) findViewById(R.id.requirements);
        password_match = (TextView) findViewById(R.id.password_match);
        message = (TextView) findViewById(R.id.message);

        password.addTextChangedListener(new TextWatcher() { // Setting textWatcher for password box, we can tell user if the password is strong
            @Override
            public void afterTextChanged(Editable s) {
                password_text = s.toString(); // Checking i the password is strong password
                int number = 0;
                int upper = 0;
                int lower = 0;
                if(password.getText().toString().length() >= 8){ // password must be at least 8 characters long
                    for(int i = 0; i < password_text.length(); i++){
                        char c = password_text.charAt(i);
                        if(Character.isUpperCase(c)){ // if no character has been uppercase letter
                            upper++;
                        }
                        if(Character.isLowerCase(c)){ // if no character has been lowercase letter
                            lower++;
                        }
                        if(Character.isDigit(c)){ // if no character has been a number
                            number++;
                        }
                        if (upper > 0 && lower > 0 && number > 0){ // if the password have a uppercase, a lowercase and a number character
                            System.out.println("Kaikki hyvin");
                            password_requirements.setText("");
                            break;
                        }
                        if(upper == 0){ // tells user to use uppercase letter
                            System.out.println("ISOJA");
                            password_requirements.setText(getResources().getString(R.string.contain_upper));
                        }
                        if(lower == 0){ // tells user to use lowercase letter
                            System.out.println("PIENIÄ");
                            password_requirements.setText(getResources().getString(R.string.contain_lower));
                        }
                        if(number == 0){ // tells user to use number
                            System.out.println("NUMEROITA");
                            password_requirements.setText(getResources().getString(R.string.contain_numbers));
                        }
                    }
                }
                else{ // if the password is under 8 characters long
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
        if (password2.getText().toString().equals(password_text) && (!password_text.equals("")) && (!username.getText().toString().equals("")) && (!name.getText().toString().equals("")) && (!age.getText().toString().equals("")) && (!city.getText().toString().equals("")) && (!email.getText().toString().equals(""))) {
            User user = new User(); // Create a new user and get information from edittexts
            user.setUsername(username.getText().toString());
            user.setName(name.getText().toString());
            user.setAge(Integer.valueOf((age.getText().toString())));
            user.setCity(city.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString()); // setPassword method hashes the password
            user_list.add(user); // add user to user_list, using to save multiple users
            log_in_message = sign_in(username.getText().toString(), password.getText().toString(), v);
            message.setText(log_in_message);
        } else {
            message.setText(getResources().getString(R.string.error));
        }
    }

    public String sign_in(String username, String password, View v) { // let user sign in and use the app
        System.out.println(user_list.size());
        if (username.equals("") || password.equals("")){ // user needs to fill both fields to sign in
            result = c.getString(R.string.fill_both);
        }else if (user_list.size() == 0){ // if there is no users created in this phone
            System.out.println("testi1");
            result = c.getString(R.string.no_user);
        }else {
            for (int i = 0; i < user_list.size(); i++) { // check if given username exists and if the password is correct
                if (user_list.get(i).getUsername().equals(username)) {
                    if (user_list.get(i).getPassword().equals(encrypt(password))) {
                        result = getString(R.string.welcome);
                        Intent intent = new Intent(v.getContext(), Begin_screen.class);
                        startActivityForResult(intent, 0);
                    } else {
                        System.out.println("testi2"); // if user is found, but password is not correct
                        result = getString(R.string.wrong_password);
                    }
                } else {
                    System.out.println("testi3"); // if no user on given username is found
                    result = getString(R.string.no_user);
                }
            }
        }
        return result; // returns a feedback for user
    }

    public static String encrypt(String input) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            //System.out.println(hashtext);
            // return the HashText
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
