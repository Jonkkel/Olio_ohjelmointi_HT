package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Add_data_food extends Fragment implements SeekBar.OnSeekBarChangeListener{

    SeekBar cheesebar, vegetablebar, dairybar, eggbar, ricebar;
    CheckBox lowCarbon;
    double cheese = 0, dairy = 0, rice = 0, vegetables = 0, eggs = 0;
    TextView cheese_amount, dairy_amount, rice_amount, vegetable_amount, egg_amount;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_data_food, container, false);

        cheese_amount = (TextView) v.findViewById(R.id.cheesebar_amount);
        dairy_amount = (TextView) v.findViewById(R.id.dairybar_amount);
        rice_amount = (TextView) v.findViewById(R.id.ricebar_amount);
        vegetable_amount = (TextView) v.findViewById(R.id.vegetablebar_amount);
        egg_amount = (TextView) v.findViewById(R.id.eggbar_amount);

        lowCarbon = (CheckBox) v.findViewById(R.id.checkBox2);

        cheesebar = (SeekBar) v.findViewById(R.id.cheeseBar);
        dairybar = (SeekBar) v.findViewById(R.id.dairyBar);
        ricebar = (SeekBar) v.findViewById(R.id.riceBar);
        vegetablebar = (SeekBar) v.findViewById(R.id.vegetableBar);
        eggbar = (SeekBar) v.findViewById(R.id.eggBar);

        cheesebar.setOnSeekBarChangeListener(this);
        dairybar.setOnSeekBarChangeListener(this);
        ricebar.setOnSeekBarChangeListener(this);
        vegetablebar.setOnSeekBarChangeListener(this);
        eggbar.setOnSeekBarChangeListener(this);

        cheese_amount.setText(cheese + " " + v.getResources().getString(R.string.add_data_unit1));
        dairy_amount.setText(dairy + " " + v.getResources().getString(R.string.add_data_unit1));
        rice_amount.setText(rice + " " + v.getResources().getString(R.string.add_data_unit1));
        vegetable_amount.setText(vegetables + " " + v.getResources().getString(R.string.add_data_unit1));
        egg_amount.setText(eggs + " " + v.getResources().getString(R.string.add_data_unit2));
        return v;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.equals(cheesebar)){
            cheese = (double) progress /100;
            cheese_amount.setText(cheese + " " + getView().getResources().getString(R.string.add_data_unit1));
        }else if(seekBar.equals(dairybar)){
            dairy = (double) progress /10;
            dairy_amount.setText(dairy + " " + getView().getResources().getString(R.string.add_data_unit1));
        }else if(seekBar.equals(ricebar)){
            rice = (double) progress /10;
            rice_amount.setText(rice + " " + getView().getResources().getString(R.string.add_data_unit1));
        }else if(seekBar.equals(vegetablebar)){
            vegetables = (double) progress /10;
            vegetable_amount.setText(vegetables + " " + getView().getResources().getString(R.string.add_data_unit1));
        }else if(seekBar.equals(eggbar)){
            eggs = (double) progress;
            egg_amount.setText(eggs + " " + getView().getResources().getString(R.string.add_data_unit2));
        }

    }

    @Override
    public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(android.widget.SeekBar seekBar) {

    }
}
