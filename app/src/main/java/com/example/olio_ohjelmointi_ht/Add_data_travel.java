package com.example.olio_ohjelmointi_ht;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Add_data_travel extends Fragment {

    // For car and motorcycle
    EditText car_distance, passengers, moped_distance, moped_consumption;
    Spinner car_size, car_year, car_fuel;
    CheckBox shared_car;

    // For public transportation
    EditText bus_distance, train_distance, tram_distance, subway_distance, longBus_distance, longTrain_distance;

    // For boat trips and flights
    EditText boat_trip1, boat_trip2, boat_trip3, flight_fin, flight_eu, flight_canarian, flight_continental;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_data_travel, container, false);
        // For car and motorcycle
        car_distance = (EditText) v.findViewById(R.id.drivingDistance);
        passengers = (EditText) v.findViewById(R.id.passengers);
        moped_distance = (EditText) v.findViewById(R.id.moped_distance);
        moped_consumption = (EditText) v.findViewById(R.id.moped_consumption);
        car_size = (Spinner) v.findViewById(R.id.car_size_spinner);
        car_year = (Spinner) v.findViewById(R.id.car_year_spinner);
        car_fuel = (Spinner) v.findViewById(R.id.car_fuel_spinner);
        shared_car = (CheckBox) v.findViewById(R.id.sharedCarBox);

        // For public transportation
        bus_distance = (EditText) v.findViewById(R.id.bus_trip);
        train_distance = (EditText) v.findViewById(R.id.train_trip);
        tram_distance = (EditText) v.findViewById(R.id.spora_trip);
        subway_distance = (EditText) v.findViewById(R.id.subway_trip);
        longBus_distance = (EditText) v.findViewById(R.id.bus_distance);
        longTrain_distance = (EditText) v.findViewById(R.id.train_distance);

        // For boat trips and flights
        boat_trip1 = (EditText) v.findViewById(R.id.he_ta_amount);
        boat_trip2 = (EditText) v.findViewById(R.id.he_tra_amount);
        boat_trip3 = (EditText) v.findViewById(R.id.he_tu_amount);
        flight_fin = (EditText) v.findViewById(R.id.inside_fin);
        flight_eu = (EditText) v.findViewById(R.id.inside_eu);
        flight_canarian = (EditText) v.findViewById(R.id.canarian);
        flight_continental = (EditText) v.findViewById(R.id.continental);

        return v;

    }
}
