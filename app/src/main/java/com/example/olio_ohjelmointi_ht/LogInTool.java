package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;

import android.content.Context;

import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class LogInTool implements Serializable {


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

    public String create_user(String username, String name, String age, String city, String email, String password, String password2) throws IOException { // adds user to user_list, creates user
        if (password2.equals(password) && (!password.equals("")) && (!username.equals("")) && (!name.equals("")) && (!age.equals("")) && (!city.equals("")) && (!email.equals(""))) {
            User user = new User(); // Create a new user and get information from edittexts
            user.setUsername(username);
            user.setName(name);
            user.setAge(Integer.parseInt((age)));
            user.setCity(city);
            user.setEmail(email);
            user.setPassword(password); // setPassword method hashes the password
            user_list.add(user); // add user to user_list, using to save multiple users
            writeTextFile(user);
            log_in_message = sign_in2(username, password);
            //message.setText(log_in_message);
        } else {
            //message.setText(getResources().getString(R.string.error));
            log_in_message = c.getString(R.string.error);
        }
        return log_in_message;
    }

    // encrypt password
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
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    public String sign_in2(String username, String password) throws IOException { // let user sign in and use the app
        if (username.equals("") || password.equals("")) { // user needs to fill both fields to sign in
            result = c.getString(R.string.fill_both);
        } else if (c.getFilesDir().list().length == 0) { // if there is no users created in this phone // if files folder is empty
            //System.out.println("testi1");
            result = c.getString(R.string.no_user); // gives feedback to user
        } else { // if file with users name is found, let's check if the given password is right
            for (File file : c.getFilesDir().listFiles()) { // check if given username exists and if the password is correct
                //System.out.println("fuletulostin:" + file.toString());
                File file2 = new File(c.getFilesDir() + File.separator + username);
                //System.out.println("file2:" + file2 .toString());
                if (file.equals(file2)) { // if right file is found
                    File fileName = new File(file,   "User_Info_" + username + ".txt"); // users info is in this folder if found
                    FileInputStream fIn = new FileInputStream(fileName);
                    ObjectInputStream is = new ObjectInputStream(fIn);
                    try {
                        System.out.println(fileName.toString());
                        User user1 = (User) is.readObject(); // read the object User from file
                        //System.out.println(user1.getName());
                        is.close(); // close FileInputStream
                        fIn.close(); // close ObjectInputStream
                        if(user1.getPassword().equals(encrypt(password))){ // if the given password is the same as the one in the file
                            SharedPreferences.Editor editor = c.getSharedPreferences("User", MODE_PRIVATE).edit();
                            editor.putString("Current User", username);
                            editor.apply();
                            result = c.getString(R.string.welcome); // feedback for user
                        }else { // if the password is wrong
                            //System.out.println("testi2");
                            result = c.getString(R.string.wrong_password);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("testi3"); // if no user on given username is found
                    result = c.getString(R.string.no_user);
                }
            }
        }

        return result; // returns a feedback for user
    }

    public void writeTextFile(User user) {
        File directory = new File(c.getFilesDir() + File.separator + user.getUsername()); // create a folder
        if (!directory.exists()) // if directory doesn't exist, create it
            directory.mkdir();
        File newFile = new File(directory,  "User_Info_" + user.getUsername() + ".txt"); // create txt file for users information
        if(!newFile.exists()){ // if the file doesn't exist, create it
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //System.out.println(newFile.toString());
            FileOutputStream fOut =  new FileOutputStream(newFile); // write users information to file
            ObjectOutputStream outputWriter = new ObjectOutputStream(fOut);
            outputWriter.writeObject(user); // write User object to the file
            outputWriter.close(); // close file
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

   /* public String sign_in(String username, String password) { // let user sign in and use the app
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

    public String sign_in2(String username, String password) throws IOException { // let user sign in and use the app
        //System.out.println(user_list.size());
        //ÄLÄ KOSKE
        if (username.equals("") || password.equals("")) { // user needs to fill both fields to sign in
            result = c.getString(R.string.fill_both);
            // JOS EI LÖYDY TIEDOSTOJA
            // c.getFilesDir() + File.separator + username + File.separator + username TIEDOSTO JOKA HALUTAAN LÖYTÄÄ JA LUKEA

        } else if (c.getFilesDir().list().length == 0) { // if there is no users created in this phone // if files folder is empty
            System.out.println("testi1");
            result = c.getString(R.string.no_user);

        } else { // JOS LÖYTYY TIEDOSTO OIKEELLA NIMELLÄ JA TSEKKAA SALIS
            for (File file : c.getFilesDir().listFiles()) { // check if given username exists and if the password is correct
                File file2 = new File(c.getFilesDir() + File.separator + username);
                if (file.equals(file2)) {
                    System.out.println("testitesti");// JOS TIEDOSTON NIMI EQUALS
                    //File file2 = new File("/data/user/0/com.example.olio_ohjelmointi_ht/files/sara");

                    FileReader fr = new FileReader(file + File.separator + "User_Info_" + username + ".txt");  //Creation of File Reader object
                    BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
                    String[] words = null;
                    String content = null;
                    while ((content = br.readLine()) != null) {   //Reading Content from the file
                        words = content.split(":");  //Split the word using :
                        System.out.println("testitestitesti");
                        for (String word : words) {
                            if (word.equals(encrypt(password))) {   //Search for the given word
                                result = c.getString(R.string.welcome);
                            } // IF

                            else { // JOS KRYPTATTU SALIS ON VÄÄRIN, ÄLÄ KOSKE
                                System.out.println("testi2"); // if user is found, but password is not correct
                                result = c.getString(R.string.wrong_password);
                            } // ELSE
                        }// FOR
                    }

                }else{ // ÄLÄKOSKEE
                    System.out.println("testi3"); // if no user on given username is found
                    result = c.getString(R.string.no_user);
                }
            }
        }

        return result; // returns a feedback for user
    }*/

// make folder and write file for each user
    /*
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
    }*/
