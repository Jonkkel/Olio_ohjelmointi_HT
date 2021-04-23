package com.example.olio_ohjelmointi_ht;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Begin_add_data_screen extends Fragment{

    Button add_food;
    Button add_drink;
    Button add_trip;
    Button add_clothes;
    SeekBar


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // find buttons fromm fragment
        Context c = getActivity();
        View v = inflater.inflate(R.layout.fragment_add_data_screen, container, false);
        add_food = (Button) v.findViewById(R.id.add_food_button);
        add_drink = (Button) v.findViewById(R.id.add_drink_button);
        add_trip = (Button) v.findViewById(R.id.add_travel_button);
        add_clothes = (Button) v.findViewById(R.id.add_clothes_button);

        add_food.setOnClickListener(this);
        add_drink.setOnClickListener(this);
        add_trip.setOnClickListener(this);
        add_clothes.setOnClickListener(this);

        return v;
    }
/* Voi käyttää ja kannattaa käyttää, jos on tekstikentää jonka tekstiä haluaa muokata.
    Ajetaan sen jälkeen kun fragmentti on luotu - Turvallisempi metodi kuin ylempi
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    */

}