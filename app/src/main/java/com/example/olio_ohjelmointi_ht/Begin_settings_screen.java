package com.example.olio_ohjelmointi_ht;

import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Begin_settings_screen extends Fragment{

    Button change_username_button;
    Button change_language_button;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context con = getActivity();
        SettingTool settingTool = SettingTool.getInstance(con);
        settingTool.loadLocale();
        View v = inflater.inflate(R.layout.fragment_settings_screen, container, false);
        change_username_button = (Button) v.findViewById(R.id.change_username_btn);
        change_language_button = (Button) v.findViewById(R.id.change_language_btn);
        change_username_button.setOnClickListener(item -> change_fragment("change username"));
        change_language_button.setOnClickListener(item -> change_fragment("change language"));
        return v;
    }

    public void change_fragment(String type){
        Fragment fragment = null;
        if (type.equals("change username")){
            fragment = new Settings_change_username();
        }else if(type.equals("change city")){

        }else if(type.equals("change age")){

        }else if(type.equals("change asd")){

        }else if(type.equals("change language")){
            fragment = new Settings_change_language();
        }else{

        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.settings_framelayout, fragment);
        transaction.commit();
    }
}