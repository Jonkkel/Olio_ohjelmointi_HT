package com.example.olio_ohjelmointi_ht;
import android.app.AlertDialog;
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

public class Begin_settings_screen extends Fragment{

    Button change_username_button;
    Button change_language_button;
    int checked = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context con = getActivity();
        SettingTool settingTool = SettingTool.getInstance(con);
        View v = inflater.inflate(R.layout.fragment_settings_screen, container, false);
        change_username_button = (Button) v.findViewById(R.id.change_username_btn);
        change_language_button = (Button) v.findViewById(R.id.change_language_btn);
        change_username_button.setOnClickListener(item -> change_fragment("change username"));
        change_language_button.setOnClickListener(item -> showChangeLanguageDialog(settingTool));
        return v;
    }
/* Voi käyttää ja kannattaa käyttää, jos on tekstikentää jonka tekstiä haluaa muokata.
    Ajetaan sen jälkeen kun fragmentti on luotu - Turvallisempi metodi kuin ylempi
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
*/

    public void change_fragment(String type){
        Fragment fragment = null;
        if (type.equals("change username")){
            fragment = new Settings_change_username();
        }else if(type.equals("change city")){

        }else if(type.equals("change age")){

        }else if(type.equals("change asd")){

        }else{

        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.settings_framelayout, fragment);
        transaction.commit();
    }
    public void showChangeLanguageDialog(SettingTool settingTool){
        final String[] listItems = {"English", "Suomi", "Svenska"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Language...");
        builder.setSingleChoiceItems(listItems, checked, (dialog, which) -> {
            if(which == 0){
                settingTool.setLocale("en");
            }else if(which == 1){
                settingTool.setLocale("fi");
            }
            getFragmentManager()
                    .beginTransaction()
                    .detach(Begin_settings_screen.this)
                    .attach(Begin_settings_screen.this)
                    .commit();
            checked = which;
            dialog.dismiss();
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();
    }


}