package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SettingTool {
    String username;
    int age;
    String password;
    String email;
    String city;

    Context c;
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
        this.c = con;
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
        c.getResources().updateConfiguration(config, c.getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = c.getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = c.getSharedPreferences("settings", MODE_PRIVATE);
        String language = prefs.getString("My_lang", "");
        setLocale(language);
    }
}
