package com.example.olio_ohjelmointi_ht;
import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Begin_add_data_screen extends Fragment implements View.OnClickListener{

    Button add_food, add_housing, add_travel, add_consumption, add_waste;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // find buttons fromm fragment
        Context c = getActivity();
        View v = inflater.inflate(R.layout.fragment_add_data_screen, container, false);
        add_food = (Button) v.findViewById(R.id.add_food_button);
        add_housing = (Button) v.findViewById(R.id.add_housing_button);
        add_travel = (Button) v.findViewById(R.id.add_travel_button);
        add_consumption = (Button) v.findViewById(R.id.add_consumption_button);
        add_waste = (Button) v.findViewById(R.id.add_waste_button);

        add_food.setOnClickListener(this);
        add_housing.setOnClickListener(this);
        add_travel.setOnClickListener(this);
        add_consumption.setOnClickListener(this);
        add_waste.setOnClickListener(this);

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        if (view.getId() == R.id.add_food_button){
            fragment = new Add_data_food();
        }else if(view.getId() == R.id.add_housing_button){
            fragment = new Add_data_housing();
        }else if(view.getId() == R.id.add_travel_button){
            fragment = new Add_data_travel();
        }else if(view.getId() == R.id.add_consumption_button){
            fragment = new Add_data_consumption();
        }else if(view.getId() == R.id.add_waste_button){
            fragment = new Add_data_waste();
        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.Main_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // showing the back button in action bar
    }

/* Voi käyttää ja kannattaa käyttää, jos on tekstikentää jonka tekstiä haluaa muokata.
    Ajetaan sen jälkeen kun fragmentti on luotu - Turvallisempi metodi kuin ylempi
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    */

}