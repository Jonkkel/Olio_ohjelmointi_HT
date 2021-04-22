package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SettingTool {

    Context context;


    @SuppressLint("StaticFieldLeak")
    private static SettingTool settingTool = null; // singleton

    public static SettingTool getInstance(Context con) {
        if (settingTool == null) {
            settingTool = new SettingTool();
            settingTool.Settings(con);
        }
        return settingTool; // return only one and same LogInTool
    }
    public void Settings(Context con){
        // Getting context so we can run androidstudio commands
        this.context = con;
    }

    public void changeUsername(String newUsername){
        User user = getUserInformation();
        if (user == null){
            System.out.println("Error");
        }else{

        }
    }


    public Boolean checkUsernameAvailability(String username){
        File directory = new File(context.getFilesDir() + File.separator + username); // create a folder
        if (!directory.exists()) {
            return true;
        }else{
            return false;
        }
    }

    public User getUserInformation(){
        SharedPreferences prefs = context.getSharedPreferences("User", MODE_PRIVATE);
        String cUser = prefs.getString("Current User", "");
        File directory = new File(context.getFilesDir() + File.separator + cUser);
        File fileName = new File(directory,   "User_Info_" + cUser + ".txt");
        try{
            FileInputStream fIn = new FileInputStream(fileName);
            ObjectInputStream is = new ObjectInputStream(fIn);
            User user = (User) is.readObject();
            fIn.close();
            is.close();
            directory.delete();
            fileName.delete();
            return user;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveUserInformation(User user){
        File directory = new File(context.getFilesDir() + File.separator + user.getUsername()); // create a folder
        if (!directory.exists()) {
            directory.mkdir();
        }
        File newFile = new File(directory,  "User_Info_" + user.getUsername() + ".txt"); // create txt file for users information
        if(!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println(newFile.toString());
            FileOutputStream fOut =  new FileOutputStream(newFile); // write users information to file
            ObjectOutputStream outputWriter = new ObjectOutputStream(fOut);
            outputWriter.writeObject(user);
            outputWriter.close(); // close file
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = context.getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = context.getSharedPreferences("settings", MODE_PRIVATE);
        String language = prefs.getString("My_lang", "");
        setLocale(language);
    }

    public int checkLanguage(){
        int position = -1;
        SharedPreferences prefs = context.getSharedPreferences("settings", MODE_PRIVATE);
        String language = prefs.getString("My_lang", "en");
        if (language.equals("en")){
            position = 0;
        }else if (language.equals("fi")){
            position = 1;
        }else if (language.equals("sv")){
            position = 2;
        }
        return position;
    }
}
