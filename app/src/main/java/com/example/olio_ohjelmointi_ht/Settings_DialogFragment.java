package com.example.olio_ohjelmointi_ht;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Settings_DialogFragment extends DialogFragment {
    SettingTool settingTool;
    Dialog dialog = null;
    EditText editText;

    static Settings_DialogFragment newInstance() {
        return new Settings_DialogFragment();
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        settingTool = SettingTool.getInstance(getActivity());
        settingTool.loadLocale();
        switch (getArguments().getString("type")){
            case ("username"):
                return super.onCreateDialog(savedInstanceState);
                //dialog = showChangeUsernameDialog();
                //break;
            case ("age"):
                //dialog = showChangeAgeDialog();
                break;
            case ("city"):
                //dialog = showChangeCityDialog();
                break;
            case ("email"):
                //dialog = showChangeEmailDialog();
                break;
            case ("language"):
                dialog = showChangeLanguageDialog();
                break;
        }
        return dialog;
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_settings_chance_language, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.inEmail);
        editText.setText("MOIMOIMOI");
    }

    public AlertDialog showChangeLanguageDialog() {
        final String[] listItems = {"English", "Suomi", "Svenska"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int position =  settingTool.checkLanguage();
        builder.setTitle(getResources().getString(R.string.choose_language));
        builder.setSingleChoiceItems(listItems, position, (dialog, which) -> {
            if(which == 0){
                settingTool.setLocale("en");
            }else if(which == 1){
                settingTool.setLocale("fi");
            }else if(which == 2){
                //TODO
                //settingTool.setLocale("sv");
            }
        });
        builder.setPositiveButton("Ok", (dialog, which) -> {
            assert getFragmentManager() != null;
            Fragment frg = getFragmentManager().findFragmentByTag("TAG");
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            assert frg != null;
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
            dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dismiss();
        });
        AlertDialog dialog = builder.create();
        //dialog.show();
        return dialog;
    }
    public Dialog showChangeUsernameDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment2_settings_chance_language);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.title);
        text.setText("Android custom dialog example!");

        Button dialogButton = (Button) dialog.findViewById(R.id.btnDone);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(i -> dialog.dismiss());
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }
}
