package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class Add_data_housing extends Fragment implements View.OnClickListener {
    Button homeButton, electricityButton, heatingButton, goodsButton;
    ConstraintLayout mainView, homeView, heatingView, electricityView, goodsView;

    RadioGroup homeRadioGroup, heatingRadioGroup;

    // Home section variables
    RadioButton detachedHouse, flatHouse, terracedHouse;
    EditText livingSpace, yearOfConstruction, numberOfFloors;

    // Heating section variables
    EditText districtHeatingAmount, oilHeatingAmount;
    CheckBox additionalWoodHeating, additionalAirPumpHeating, additionalOwnElectricityHeating;
    RadioButton oilHeating, groundHeat, electricityHeat, woodHeat;

    // Electricity section variables
    EditText electricityUsage, electricityGreenPercent;

    // Goods section variables
    EditText goodsFurniture, goodsAppliance, goodsTableware, goodsRenovation, goodsCleaning;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_data_housing, container, false);
        homeView = v.findViewById(R.id.Home_layout);
        heatingView = v.findViewById(R.id.Heating_layout);
        electricityView = v.findViewById(R.id.Electricity_layout);
        goodsView = v.findViewById(R.id.Goods_layout);
        mainView = v.findViewById(R.id.Main_layout);

        homeButton = v.findViewById(R.id.Home_button);
        heatingButton = v.findViewById(R.id.Heating_button);
        electricityButton = v.findViewById(R.id.Electricity_button);
        goodsButton = v.findViewById(R.id.Goods_button);

        homeButton.setOnClickListener(this);
        heatingButton.setOnClickListener(this);
        electricityButton.setOnClickListener(this);
        goodsButton.setOnClickListener(this);

        homeRadioGroup = v.findViewById(R.id.HomeRadioGroup);
        heatingRadioGroup = v.findViewById(R.id.HeatingRadioGroup);

        // Home section
        detachedHouse = (RadioButton) getView().findViewById(R.id.detached_button);
        flatHouse = (RadioButton) getView().findViewById(R.id.flat_button);
        terracedHouse = (RadioButton) getView().findViewById(R.id.terraced_button);
        livingSpace = (EditText) getView().findViewById(R.id.livingSpace);
        yearOfConstruction = (EditText) getView().findViewById(R.id.Year_of_construction);
        numberOfFloors = (EditText) getView().findViewById(R.id.Number_of_floor);

        // Heating section
        districtHeatingAmount = (EditText) getView().findViewById(R.id.districtHeatingEditText);
        oilHeatingAmount = (EditText) getView().findViewById(R.id.OilEditText);
        additionalWoodHeating = (CheckBox) getView().findViewById(R.id.pellet_box);
        additionalAirPumpHeating = (CheckBox) getView().findViewById(R.id.airpump_box);
        additionalOwnElectricityHeating = (CheckBox) getView().findViewById(R.id.own_box);
        oilHeating = (RadioButton) getView().findViewById(R.id.oil_button);
        groundHeat = (RadioButton) getView().findViewById(R.id.ground_button);
        electricityHeat = (RadioButton) getView().findViewById(R.id.electricity_button);
        woodHeat = (RadioButton) getView().findViewById(R.id.pellet_button);


        // Electricity section
        electricityUsage = (EditText) getView().findViewById(R.id.electricityConsumption);
        electricityGreenPercent = (EditText) getView().findViewById(R.id.electricityGreenEnergyPercent);

        // Goods section
        goodsFurniture = (EditText) getView().findViewById(R.id.furnitureEditText);
        goodsAppliance = (EditText) getView().findViewById(R.id.appliancesEditText);
        goodsTableware = (EditText) getView().findViewById(R.id.tablewareEditText);
        goodsRenovation = (EditText) getView().findViewById(R.id.renovationEditText);
        goodsCleaning = (EditText) getView().findViewById(R.id.cleaningEditText);

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Home_button:
                if (homeView.getVisibility() == View.GONE) {
                    closeOpenLayouts();
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    homeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                    homeView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    homeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                    homeView.setVisibility(View.GONE);
                }
                break;
            case R.id.Heating_button:
                if (heatingView.getVisibility() == View.GONE) {
                    closeOpenLayouts();
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    heatingButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                    heatingView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    heatingButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                    heatingView.setVisibility(View.GONE);
                }
                break;
            case R.id.Electricity_button:
                if (electricityView.getVisibility() == View.GONE) {
                    closeOpenLayouts();
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    electricityButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                    electricityView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    electricityButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                    electricityView.setVisibility(View.GONE);
                }
                break;
            case R.id.Goods_button:
                if (goodsView.getVisibility() == View.GONE) {
                    closeOpenLayouts();
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    goodsButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                    goodsView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    goodsButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                    goodsView.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void closeOpenLayouts() {
        if (homeView.getVisibility() == View.VISIBLE) {
            homeView.setVisibility(View.GONE);
            homeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }
        if (heatingView.getVisibility() == View.VISIBLE) {
            heatingView.setVisibility(View.GONE);
            heatingButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }
        if (electricityView.getVisibility() == View.VISIBLE) {
            electricityView.setVisibility(View.GONE);
            electricityButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }
        if (goodsView.getVisibility() == View.VISIBLE) {
            goodsView.setVisibility(View.GONE);
            goodsButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }
    }
}