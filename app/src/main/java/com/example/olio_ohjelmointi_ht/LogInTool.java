package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;

import android.content.Context;

import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LogInTool {

    String password_text;
    String log_in_message;
    String result;
    Context c;

    public ArrayList<User> user_list = new ArrayList<>(); // list for multiple users

    @SuppressLint("StaticFieldLeak")
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

    public String create_user(EditText username, EditText name, EditText age, EditText city, EditText email, EditText password, EditText password2, View v) { // adds user to user_list, creates user
        if (password2.getText().toString().equals(password_text) && (!password_text.equals("")) && (!username.getText().toString().equals("")) && (!name.getText().toString().equals("")) && (!age.getText().toString().equals("")) && (!city.getText().toString().equals("")) && (!email.getText().toString().equals(""))) {
            User user = new User(); // Create a new user and get information from edittexts
            user.setUsername(username.getText().toString());
            user.setName(name.getText().toString());
            user.setAge(Integer.valueOf((age.getText().toString())));
            user.setCity(city.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString()); // setPassword method hashes the password
            user_list.add(user); // add user to user_list, using to save multiple users
            writeTextFile(user);
            log_in_message = sign_in(username.getText().toString(), password.getText().toString(), v);
            //message.setText(log_in_message);
        } else {
            //message.setText(getResources().getString(R.string.error));
            log_in_message = c.getString(R.string.error);
        }
        return log_in_message;
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
                        result = c.getString(R.string.welcome);
                    } else {
                        System.out.println("testi2"); // if user is found, but password is not correct
                        result = c.getString(R.string.wrong_password);
                    }
                } else {
                    System.out.println("testi3"); // if no user on given username is found
                    result = c.getString(R.string.no_user);
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

    // make folder and write file for each user
    public void writeTextFile(User user){
        File directory = new File(c.getFilesDir() + File.separator + user.getUsername()); // create a folder
        if(!directory.exists())
            directory.mkdir();
        File newFile = new File(directory, "User_Info_" + user.getUsername() + ".txt"); // create txt file for users information
        if(!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try  {
            FileOutputStream fOut = new FileOutputStream(newFile); // write users information to file
            OutputStreamWriter outputWriter=new OutputStreamWriter(fOut);
            outputWriter.write(c.getResources().getString(R.string.file_username) + user.getUsername() + "\n");
            outputWriter.write(c.getResources().getString(R.string.file_name) + user.getName() + "\n");
            outputWriter.write(c.getResources().getString(R.string.file_age) + user.getAge() + "\n");
            outputWriter.write(c.getResources().getString(R.string.file_city) + user.getCity() + "\n");
            outputWriter.write(c.getResources().getString(R.string.file_email) + user.getEmail() + "\n");
            outputWriter.write(c.getResources().getString(R.string.file_password) + user.getPassword() + "\n");
            outputWriter.close(); // close file
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
