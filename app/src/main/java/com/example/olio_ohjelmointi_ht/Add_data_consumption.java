package com.example.olio_ohjelmointi_ht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ConcurrentHashMap;

public class Add_data_consumption extends Fragment {
    EditText clothing;
    EditText shoes;
    EditText paper;
    EditText electronics;
    EditText recreation;
    EditText communication;
    EditText other;

    CheckBox clothing_box;
    CheckBox shoe_box;
    CheckBox paper_box;
    CheckBox electronics_box;
    CheckBox recreation_box;
    CheckBox communication_box;
    CheckBox other_box;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        clothing = (EditText) getView().findViewById(R.id.clothingText);
        shoes = (EditText) getView().findViewById(R.id.shoeText);
        paper = (EditText) getView().findViewById(R.id.paperText);
        electronics = (EditText) getView().findViewById(R.id.electronicsText);
        recreation = (EditText) getView().findViewById(R.id.recreationText);
        communication = (EditText) getView().findViewById(R.id.communicationsText);
        other = (EditText) getView().findViewById(R.id.otherText);

        clothing_box = (CheckBox) getView().findViewById(R.id.carbon_box_clothing);
        shoe_box = (CheckBox) getView().findViewById(R.id.carbon_box_shoes);
        paper_box = (CheckBox) getView().findViewById(R.id.carbon_box_paper);
        electronics_box = (CheckBox) getView().findViewById(R.id.carbon_box_electronics);
        recreation_box = (CheckBox) getView().findViewById(R.id.carbon_box_recreation);
        communication_box = (CheckBox) getView().findViewById(R.id.carbon_box_phone);
        other_box = (CheckBox) getView().findViewById(R.id.carbon_box_other);

        return inflater.inflate(R.layout.fragment_add_data_consumption, container, false);

    }
}
