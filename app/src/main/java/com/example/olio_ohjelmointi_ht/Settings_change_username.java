package com.example.olio_ohjelmointi_ht;
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

public class Settings_change_username extends Fragment{

    Button exit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_change_username, container, false);
    }
/* Voi käyttää ja kannattaa käyttää, jos on tekstikentää jonka tekstiä haluaa muokata.
    Ajetaan sen jälkeen kun fragmentti on luotu - Turvallisempi metodi kuin ylempi*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        exit = (Button) view.findViewById(R.id.exit_btn);
        exit.setOnClickListener(item ->{
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().remove(this).commit();
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
