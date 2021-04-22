package com.example.olio_ohjelmointi_ht;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
    String change, hint;
    Integer inputType = 1;
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
                change = "Change username";
                hint = "new username";
                return super.onCreateDialog(savedInstanceState);
                //break;
            case ("age"):
                change = "Change age";
                hint = "new age";
                inputType = 2;
                return super.onCreateDialog(savedInstanceState);
            case ("city"):
                change = "Change city";
                hint = "set new city";
                return super.onCreateDialog(savedInstanceState);
            case ("email"):
                change = "Change email";
                hint = "set new email";
                return super.onCreateDialog(savedInstanceState);
            case ("language"):
                dialog = showChangeLanguageDialog();
                break;
        }

        return dialog;
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_settings_change, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView dialogTitle = (TextView) getView().findViewById(R.id.title);
        TextView errorText = (TextView) getView().findViewById(R.id.title);
        dialogTitle.setText(change);

        // set hint to fragmentDialog EditText
        EditText  editText = (EditText) getView().findViewById(R.id.changeSth);
        editText.setHint(hint);
        editText.setInputType(inputType);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (getArguments().getString("type").equals("username"));{
                    String newUsername = editText.getText().toString();
                    if(settingTool.checkUsernameAvailability(newUsername)){

                    }else{

                    }
                }

            }
        });

        Button dialogButton = (Button) getView().findViewById(R.id.btnDone);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(i ->
                //SettingTool.setUsername();
                dismiss()
        );
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
