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
import java.nio.file.Path;
import java.nio.file.Paths;
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


    public Boolean checkUsernameAvailability(String username){
        File directory = new File(c.getFilesDir() + File.separator + username); // create a folder
        if (!directory.exists()) {
            return false;
        }else{
            return true;
        }
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

                Path path1 = Paths.get(String.valueOf(file));
                Path path2 = Paths.get(c.getFilesDir() + File.separator + username);
                System.out.println("path1:" + path1.toString());
                System.out.println("path2:" + path2.toString());
                if (path1.equals(path2)) {
                    System.out.println("testitesti");// JOS TIEDOSTON NIMI EQUALS
                    //File file2 = new File("/data/user/0/com.example.olio_ohjelmointi_ht/files/sara");
                    File fileName = new File(file,   "User_Info_" + username + ".txt");
                    try{
                        FileInputStream fIn = new FileInputStream(fileName);
                        ObjectInputStream is = new ObjectInputStream(fIn);
                        System.out.println(fileName.toString());
                        System.out.println("jJEE");
                        User user1 = (User) is.readObject();
                        System.out.println(user1.getName());
                        is.close();
                        fIn.close();
                        if(user1.getPassword().equals(encrypt(password+user1.getName()))){
                            SharedPreferences.Editor editor = c.getSharedPreferences("User", MODE_PRIVATE).edit();
                            editor.putString("Current User", username);
                            editor.apply();
                            result = c.getString(R.string.welcome);
                            //Begin_home_screen bhs = new Begin_home_screen();
                            //bhs.getUsername(username);
                        }else { // JOS KRYPTATTU SALIS ON VÄÄRIN, ÄLÄ KOSKE
                            System.out.println("testi2"); // if user is found, but password is not correct
                            result = c.getString(R.string.wrong_password);
                        } // ELSE
                    } catch (ClassNotFoundException | IOException e) {
                        e.printStackTrace();
                    }
                }else{ // ÄLÄKOSKEE
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

