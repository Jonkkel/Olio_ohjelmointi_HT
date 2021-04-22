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

public class Begin_settings_screen extends Fragment implements View.OnClickListener, Settings_DialogFragment.DialogListener{

    Button change_username_button;
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
        change_age_button = (Button) v.findViewById(R.id.change_age_btn);
        change_home_city_button = (Button) v.findViewById(R.id.change_home_city_btn);
        change_email_button = (Button) v.findViewById(R.id.change_email_btn);
        change_language_button = (Button) v.findViewById(R.id.change_language_btn);

        change_username_button.setOnClickListener(this);
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
                /* bundle.putString("text", "Username: ");
                bundle.putString("type", "username");
                dialogFragment.setArguments(bundle);
*/
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Settings_DialogFragment dialogFragment = new Settings_DialogFragment().newInstance();
                transaction.replace(R.id.New_setting_fragment, dialogFragment);
                transaction.commit();
                break;
            case R.id.change_age_btn:
                dialogFragment = new Settings_DialogFragment();
                bundle = new Bundle();
                bundle.putString("text", "Age: ");
                bundle.putString("type", "age");
                dialogFragment.setArguments(bundle);

                transaction = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    transaction.remove(prev);
                }
                transaction.addToBackStack(null);

                dialogFragment.show(transaction, "dialog");

                break;
            case R.id.change_home_city_btn:
                dialogFragment = new Settings_DialogFragment();
                bundle = new Bundle();
                bundle.putString("text", "City: ");
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
                bundle.putString("text", "Email: ");
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
                bundle.putString("text", "Language: ");
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

    @Override
    public void onFinishEditDialog(String inputText) {
        System.out.println("JOOEJEROJE");
    }
}