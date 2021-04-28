package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
    Context c = getContext();

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
                change = getContext().getString(R.string.change_username);
                hint = getContext().getString(R.string.new_username);
                return super.onCreateDialog(savedInstanceState);
            case ("password"):
                change = getContext().getString(R.string.change_password);
                hint = getContext().getString(R.string.new_password);
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                return super.onCreateDialog(savedInstanceState);
            case ("age"):
                change = getContext().getString(R.string.change_age);
                hint = getContext().getString(R.string.new_age);;
                inputType = 2;
                return super.onCreateDialog(savedInstanceState);
            case ("city"):
                change = getContext().getString(R.string.change_home_city);
                hint = getContext().getString(R.string.new_home_city);;
                return super.onCreateDialog(savedInstanceState);
            case ("email"):
                change = getContext().getString(R.string.change_email);
                hint = getContext().getString(R.string.new_email);;
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
        return inflater.inflate(R.layout.dialogfragment_settings_change, container, false);
    }
// TODO VAIHDA SALASANAN VAIHTO JUTUSSA VANHA SALASANA TEKSTI MYÖS SUOMEKSI SARA EI LÖYTÄNYT SITÄ KOHTAA
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogTitle = (TextView) view.findViewById(R.id.title);
        usernameError = (TextView) view.findViewById(R.id.usernameError);
        user = settingTool.getUserObject();
        oldValueHolder = (EditText) view.findViewById(R.id.oldValue);
        dialogTitle.setText(change);
        Button dialogButton = (Button) view.findViewById(R.id.btnDone);
        // set hint to fragmentDialog EditText
        editText = (EditText) view.findViewById(R.id.changeSth);
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
                    //settingTool.changeUsername(value);
                    Toast.makeText(getContext(), getContext().getString(R.string.changed_username), Toast.LENGTH_SHORT).show();
                }else if (getArguments().getString("type").equals("password")){
                    if(!(oldValueHolder.getText().toString().equals("")) && settingTool.checkPassword(oldValueHolder.getText().toString())){
                        settingTool.changePassword(value);
                        Toast.makeText(getContext(), getContext().getString(R.string.changed_password), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), getContext().getString(R.string.new_password_wrong), Toast.LENGTH_SHORT).show();
                    }
                }else if (getArguments().getString("type").equals("age")){
                    settingTool.changeUserAge(value);
                    Toast.makeText(getContext(), getContext().getString(R.string.changed_age), Toast.LENGTH_SHORT).show();
                }else if (getArguments().getString("type").equals("city")){
                    settingTool.changeUserCity(value);
                    Toast.makeText(getContext(), getContext().getString(R.string.changed_home_city), Toast.LENGTH_SHORT).show();
                }else if (getArguments().getString("type").equals("email")){
                    settingTool.changeUserEmail(value);
                    Toast.makeText(getContext(), getContext().getString(R.string.changed_email), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "This language is not available right now", Toast.LENGTH_SHORT).show();
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
        oldValueHolder.setHintTextColor(getResources().getColor(R.color.black));
        oldValueHolder.setEnabled(false);
        if (getArguments().getString("type").equals("username")){
            oldValueHolder.setHint(getResources().getString(R.string.old_username)+ " " + user.getUsername());
        }else if (getArguments().getString("type").equals("password")){
            oldValueHolder.setHintTextColor(getResources().getColor(R.color.hint));
            oldValueHolder.setHint(getResources().getString(R.string.old_password));
            oldValueHolder.setEnabled(true);
        }else if (getArguments().getString("type").equals("age")){
            oldValueHolder.setHint(getResources().getString(R.string.old_age)+ " " + user.getAge());
        }else if (getArguments().getString("type").equals("city")){
            oldValueHolder.setHint(getResources().getString(R.string.old_home_city)+ " " + user.getCity());
        }else if (getArguments().getString("type").equals("email")){
            oldValueHolder.setHint(getResources().getString(R.string.old_email)+ " " + user.getEmail());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
