package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Begin_settings_screen extends Fragment implements View.OnClickListener{

    Button change_username_button;
    Button change_password_button;
    Button change_age_button;
    Button change_home_city_button;
    Button change_email_button;
    Button change_language_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context con = getActivity();
        SettingTool settingTool = SettingTool.getInstance(con);
        settingTool.loadLocale();
        View v = inflater.inflate(R.layout.fragment_settings_screen, container, false);
        change_username_button = (Button) v.findViewById(R.id.change_username_btn);
        change_password_button = (Button) v.findViewById(R.id.change_password_btn);
        change_age_button = (Button) v.findViewById(R.id.change_age_btn);
        change_home_city_button = (Button) v.findViewById(R.id.change_home_city_btn);
        change_email_button = (Button) v.findViewById(R.id.change_email_btn);
        change_language_button = (Button) v.findViewById(R.id.change_language_btn);

        change_username_button.setOnClickListener(this);
        change_password_button.setOnClickListener(this);
        change_age_button.setOnClickListener(this);
        change_home_city_button.setOnClickListener(this);
        change_email_button.setOnClickListener(this);
        change_language_button.setOnClickListener(this);
        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.change_username_btn:

                Bundle bundle = new Bundle();
                Settings_DialogFragment dialogFragment = new Settings_DialogFragment();
                bundle.putString("type", "username");
                dialogFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    transaction.remove(prev);
                }
                transaction.addToBackStack(null);

                dialogFragment.show(transaction, "dialog");
                break;
            case R.id.change_password_btn:

                bundle = new Bundle();
                dialogFragment = new Settings_DialogFragment();
                bundle.putString("type", "password");
                dialogFragment.setArguments(bundle);

                transaction = getFragmentManager().beginTransaction();
                prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    transaction.remove(prev);
                }
                transaction.addToBackStack(null);

                dialogFragment.show(transaction, "dialog");
                break;
            case R.id.change_age_btn:
                dialogFragment = new Settings_DialogFragment();
                bundle = new Bundle();
                bundle.putString("type", "age");
                dialogFragment.setArguments(bundle);

                transaction = getFragmentManager().beginTransaction();
                prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    transaction.remove(prev);
                }
                transaction.addToBackStack(null);

                dialogFragment.show(transaction, "dialog");

                break;
            case R.id.change_home_city_btn:
                dialogFragment = new Settings_DialogFragment();
                bundle = new Bundle();
                bundle.putString("type", "city");
                dialogFragment.setArguments(bundle);

                transaction = getFragmentManager().beginTransaction();
                prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    transaction.remove(prev);
                }
                transaction.addToBackStack(null);

                dialogFragment.show(transaction, "dialog");

                break;
            case R.id.change_email_btn:
                dialogFragment = new Settings_DialogFragment();
                bundle = new Bundle();
                bundle.putString("type", "email");
                dialogFragment.setArguments(bundle);

                transaction = getFragmentManager().beginTransaction();
                prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    transaction.remove(prev);
                }
                transaction.addToBackStack(null);

                dialogFragment.show(transaction, "dialog");


                break;
            case R.id.change_language_btn:
                dialogFragment = new Settings_DialogFragment();
                bundle = new Bundle();
                bundle.putString("type", "language");
                dialogFragment.setArguments(bundle);

                transaction = getFragmentManager().beginTransaction();
                prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    transaction.remove(prev);
                }
                transaction.addToBackStack(null);

                dialogFragment.show(transaction, "dialog");
                break;
        }
    };

}