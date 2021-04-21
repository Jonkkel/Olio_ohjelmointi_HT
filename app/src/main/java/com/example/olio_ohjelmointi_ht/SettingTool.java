package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SettingTool {
    String username;
    int age;
    String password;
    String email;
    String city;
    Context context;
    int selected = -1;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        password = LogInTool.encrypt(password);
        this.password = password;
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
