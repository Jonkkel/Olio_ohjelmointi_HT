package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Settings_DialogFragment extends DialogFragment {
    SettingTool settingTool;
    Dialog dialog = null;
    EditText editText, oldValueHolder;
    String change, hint;
    Integer inputType = 1;
    TextView usernameError, dialogTitle;
    User user;
    static Settings_DialogFragment newInstance() {
        return new Settings_DialogFragment();
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        settingTool = SettingTool.getInstance(getActivity());
        settingTool.loadLocale();
        user = settingTool.getUserObject();
        switch (getArguments().getString("type")){
            case ("username"):
                change = "Change username";
                hint = "new username";
                return super.onCreateDialog(savedInstanceState);
            case ("password"):
                change = "Change password";
                hint = "New password";
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                return super.onCreateDialog(savedInstanceState);
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
        dialogTitle = (TextView) getView().findViewById(R.id.title);
        usernameError = (TextView) getView().findViewById(R.id.usernameError);

        oldValueHolder = (EditText) getView().findViewById(R.id.oldValue);
        dialogTitle.setText(change);
        Button dialogButton = (Button) getView().findViewById(R.id.btnDone);
        // set hint to fragmentDialog EditText
        editText = (EditText) getView().findViewById(R.id.changeSth);
        editText.setHint(hint);

        editText.setInputType(inputType);
        oldValueHolder.setInputType(inputType);
        setOldValue();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText.getText().toString().equals("")){
                dialogButton.setEnabled(false);
                }else{
                    dialogButton.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (getArguments().getString("type").equals("username")){
                    String newUsername = editText.getText().toString();
                    if(settingTool.checkUsernameAvailability(newUsername) && !(newUsername.equals(""))){
                        usernameError.setText(getContext().getString(R.string.usernameTaken));
                        dialogButton.setEnabled(false);
                    }else{
                        usernameError.setText("");
                        dialogButton.setEnabled(true);
                    }
                }
            }
        });

            // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(i ->{
            String value = editText.getText().toString();
            if(!(value.equals(""))){
                if (getArguments().getString("type").equals("username")){
                    settingTool.changeUsername(value);
                    Toast.makeText(getContext(), "Username changed", Toast.LENGTH_SHORT).show();
                }else if (getArguments().getString("type").equals("password")){
                    if(!(oldValueHolder.getText().toString().equals("")) && settingTool.checkPassword(oldValueHolder.getText().toString())){
                        Toast.makeText(getContext(), "password changed", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "password wrong", Toast.LENGTH_SHORT).show();
                    }
                }else if (getArguments().getString("type").equals("age")){
                    settingTool.changeUserAge(value);
                    Toast.makeText(getContext(), "Age changed", Toast.LENGTH_SHORT).show();
                }else if (getArguments().getString("type").equals("city")){
                    settingTool.changeUserCity(value);
                    Toast.makeText(getContext(), "Home city changed", Toast.LENGTH_SHORT).show();
                }else if (getArguments().getString("type").equals("email")){
                    settingTool.changeUserEmail(value);
                    Toast.makeText(getContext(), "Email changed", Toast.LENGTH_SHORT).show();
                }
            }
            dismiss();
        });
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

    @SuppressLint("SetTextI18n")
    public void setOldValue(){
        oldValueHolder.setEnabled(false);
        if (getArguments().getString("type").equals("username")){
            oldValueHolder.setHint(getResources().getString(R.string.old_username)+ " " + user.getUsername());
        }else if (getArguments().getString("type").equals("password")){
            oldValueHolder.setHint("Write old password");
            oldValueHolder.setEnabled(true);
        }else if (getArguments().getString("type").equals("age")){
            oldValueHolder.setHint(getResources().getString(R.string.old_username)+ " " + user.getUsername());
        }else if (getArguments().getString("type").equals("city")){
            oldValueHolder.setHint(getResources().getString(R.string.old_username)+ " " + user.getUsername());
        }else if (getArguments().getString("type").equals("email")){
            oldValueHolder.setHint(getResources().getString(R.string.old_username)+ " " + user.getUsername());
        }
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
