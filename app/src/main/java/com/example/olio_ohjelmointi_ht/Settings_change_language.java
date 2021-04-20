package com.example.olio_ohjelmointi_ht;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Settings_change_language extends DialogFragment {
    int position = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_settings_chance_language, container, false);
        SettingTool settingTool = SettingTool.getInstance(getContext());
        settingTool.loadLocale();
        showChangeLanguageDialog(settingTool);
        return view;
    }

    public void showChangeLanguageDialog(SettingTool settingTool) {
        final String[] listItems = {"English", "Suomi", "Svenska"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        position = settingTool.checkLanguage();
        builder.setTitle(getResources().getString(R.string.choose_language));
        builder.setSingleChoiceItems(listItems, position, (dialog, which) -> {
            if(which == 0){
                settingTool.setLocale("en");
            }else if(which == 1){
                settingTool.setLocale("fi");
            }else if(which == 2){
                settingTool.setLocale("sv");
            }
        });
        builder.setPositiveButton("Ok", (dialog, which) -> {
            Fragment frg = null;
            frg = getActivity().getSupportFragmentManager().findFragmentByTag("TAG");
            final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
