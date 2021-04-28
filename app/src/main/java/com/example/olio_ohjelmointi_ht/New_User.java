package com.example.olio_ohjelmointi_ht;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class New_User extends Fragment {
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
    TextView username_requirements;
    Button create;
    LogInTool LIT;
    String log_in_message;
    Context c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_user, container, false);
        c = getContext();
        LIT = LogInTool.getInstance(c);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Finding text boxes etc. from the layout
        username = (EditText) view.findViewById(R.id.username);
        name = (EditText) view.findViewById(R.id.name);
        age = (EditText) view.findViewById(R.id.age);
        city = (EditText) view.findViewById(R.id.homeCity);
        email = (EditText) view.findViewById(R.id.email);
        username_requirements = (TextView) view.findViewById(R.id.usenameRequirements);
        password = (EditText) view.findViewById(R.id.password);
        password2 = (EditText) view.findViewById(R.id.password2);
        password_requirements = (TextView) view.findViewById(R.id.requirements);
        password_match = (TextView) view.findViewById(R.id.password_match);
        message = (TextView) view.findViewById(R.id.message);
        create = (Button) view.findViewById(R.id.create_button);


        username.addTextChangedListener(new TextWatcher() { // Setting textWatcher for password box, we can tell user if the password is strong
            @Override
            public void afterTextChanged(Editable s) {
                passwordRequirementChecker(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if(LIT.checkUsernameAvailability(username.getText().toString()) && !(username.getText().toString()).equals("")){
                    username_requirements.setText(getResources().getString(R.string.usernameTaken));
                }else{
                    username_requirements.setText("");
                }
            }

        });

        password.addTextChangedListener(new TextWatcher() { // Setting textWatcher for password box, we can tell user if the password is strong
            @Override
            public void afterTextChanged(Editable s) {
                passwordRequirementChecker(s);
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
                checkPasswordMatch();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
            }

        });
        create.setOnClickListener(item -> {
            try {
                create_button();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void passwordRequirementChecker(Editable s){
        password_text = s.toString(); // Checking i the password is strong password
        int number = 0;
        int upper = 0;
        int lower = 0;
        int special_count = 0;
        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>,.-?{}\\[\\]~]");
        if(password.getText().toString().length() >= 12){ // password must be at least 8 characters long
            for(int i = 0; i < password_text.length(); i++){
                char c = password_text.charAt(i);
                Matcher hasSpecial = special.matcher(s);
                if(Character.isUpperCase(c)){ // if no character has been uppercase letter
                    upper++;
                }
                if(Character.isLowerCase(c)){ // if no character has been lowercase letter
                    lower++;
                }
                if(Character.isDigit(c)){ // if no character has been a number
                    number++;
                }
                if(hasSpecial.find()){ // if no character has been a special character
                    special_count++;
                }
                if (upper > 0 && lower > 0 && number > 0 && special_count > 0){ // if the password have a uppercase, a lowercase and a number character
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
                if(special_count == 0){ // tells user to use special character
                    System.out.println("SPESSUJA");
                    password_requirements.setText(getResources().getString(R.string.contain_special));
                }
            }
        }
        else{ // if the password is under 8 characters long
            password_requirements.setText(getResources().getString(R.string.password_length));
        }
    }

    public void checkPasswordMatch(){
        if (password2.getText().toString().equals(password_text)) {
            password_match.setTextColor(getResources().getColor(R.color.login_screen_color));
            password_match.setText(getResources().getString(R.string.password_correct));
        } else {
            password_match.setTextColor(getResources().getColor(R.color.red));
            password_match.setText(getResources().getString(R.string.password_incorrect));
        }
    }

    public void create_button() throws IOException {
        System.out.println("TULEEKO TÄNNE");
        if(password_requirements.getText().equals("") && username_requirements.getText().equals("")) {
            log_in_message = LIT.create_user(username.getText().toString(), name.getText().toString(), age.getText().toString(), city.getText().toString(), email.getText().toString(), password.getText().toString(), password2.getText().toString());
            if (log_in_message.equals(c.getString(R.string.welcome))) {
                message.setText(log_in_message);
                Intent intent = new Intent(getActivity(), Begin.class);
                startActivity(intent);
                getActivity().finish();
                //((Activity)getActivity()).overridePendingTransition(0,0);
            } else {
                // if no user is found, should never happen
                message.setText(log_in_message);
            }
        }
        else{
            message.setTextColor(getResources().getColor(R.color.red));
            message.setText(getResources().getString(R.string.password_not_good));
        }
    }

}
