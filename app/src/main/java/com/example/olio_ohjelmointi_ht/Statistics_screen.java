package com.example.olio_ohjelmointi_ht;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Statistics_screen extends Fragment{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics_screen, container, false);
    }
/* Voi käyttää ja kannattaa käyttää, jos on tekstikentää jonka tekstiä haluaa muokata.
    Ajetaan sen jälkeen kun fragmentti on luotu - Turvallisempi metodi kuin ylempi
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    */
}