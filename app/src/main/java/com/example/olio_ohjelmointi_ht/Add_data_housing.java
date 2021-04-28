package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;

public class Add_data_housing extends Fragment implements View.OnClickListener {
    Button homeButton, electricityButton, heatingButton, goodsButton, submitData;
    ConstraintLayout mainView, homeView, heatingView, electricityView, goodsView;

    RadioGroup homeRadioGroup, heatingRadioGroup;

    // Home section variables
    EditText livingSpace, yearOfConstruction, numberOfFloors, familySize;

    // Heating section variables
    EditText districtHeatingAmount, oilHeatingAmount;
    CheckBox additionalWoodHeating, additionalAirPumpHeating, additionalOwnElectricityHeating;

    int heatingOilConsumption = 0, districtHeatingConsumption = 0;

    // Electricity section variables
    EditText electricityUsage, electricityGreenPercent;

    // Goods section variables
    EditText goodsFurniture, goodsAppliance, goodsTableware, goodsRenovation, goodsCleaning;


    String houseType = "family", heatingType = "";
    URL url;
    CallApi CAPI;

    double area;
    int buildYear;
    int floors = 1;
    int family = 1;

    int electricityConsumption = 0;
    int greenElectricityPercentage = 0;

    int appliancePurchases = 0;
    int furniturePurchases = 0;
    int renovationPurchases = 0;
    int miscPurchases = 0;
    int cleaningPurchases = 0;

    String filename = "housingData.csv";
    @SuppressLint({"NonConstantResourceId", "CutPasteId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_data_housing, container, false);
        Context con = getActivity();
        CAPI = new CallApi(con, filename);
        homeView = v.findViewById(R.id.Home_layout);
        heatingView = v.findViewById(R.id.Heating_layout);
        electricityView = v.findViewById(R.id.Electricity_layout);
        goodsView = v.findViewById(R.id.Goods_layout);
        mainView = v.findViewById(R.id.Main_layout);

        homeButton = v.findViewById(R.id.Home_button);
        heatingButton = v.findViewById(R.id.Heating_button);
        electricityButton = v.findViewById(R.id.Electricity_button);
        goodsButton = v.findViewById(R.id.Goods_button);
        submitData = v.findViewById(R.id.housingSubmitData);

        homeButton.setOnClickListener(this);
        heatingButton.setOnClickListener(this);
        electricityButton.setOnClickListener(this);
        goodsButton.setOnClickListener(this);
        submitData.setOnClickListener(this);

        homeRadioGroup = v.findViewById(R.id.HomeRadioGroup);
        homeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId) {
                // Api is bugged and gives error message: "Message": "An error has occurred." when flat or row is tried to input"
                // We also tested this on https://ilmastodieetti.ymparisto.fi/ilmastodieetti/swagger/ui/index#/
                case R.id.detached_button:
                    houseType = "family";
                    break;
                case R.id.flat_button:
                    //houseType = "flat";
                    break;
                case R.id.terraced_button:
                    //houseType = "row";
                    break;
            }
        });
        heatingRadioGroup = v.findViewById(R.id.HeatingRadioGroup);
        heatingRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case R.id.district_button:
                    heatingType = "district";
                    break;
                case R.id.oil_button:
                    heatingType = "oil";
                    break;
                case R.id.ground_button:
                    heatingType = "pump";
                    break;
                case R.id.electricity_button:
                    heatingType = "electricity";
                    break;
                case R.id.pellet_button:
                    heatingType = "wood";
                    break;
            }
        });
        // Home section
        livingSpace = v.findViewById(R.id.drivingDistance);
        yearOfConstruction = v.findViewById(R.id.Year_of_construction);
        numberOfFloors = v.findViewById(R.id.Number_of_floor);
        familySize = v.findViewById(R.id.family_Size);

        // Heating section
        districtHeatingAmount =  v.findViewById(R.id.districtHeatingEditText);
        oilHeatingAmount = v.findViewById(R.id.OilEditText);
        additionalWoodHeating = v.findViewById(R.id.pellet_box);
        additionalAirPumpHeating = v.findViewById(R.id.airpump_box);
        additionalOwnElectricityHeating = v.findViewById(R.id.own_box);

        // Electricity section
        electricityUsage = v.findViewById(R.id.electricityConsumption);
        electricityGreenPercent = v.findViewById(R.id.electricityGreenEnergyPercent);

        // Goods section
        goodsFurniture =  v.findViewById(R.id.furnitureEditText);
        goodsAppliance =  v.findViewById(R.id.appliancesEditText);
        goodsTableware = v.findViewById(R.id.tablewareEditText);
        goodsRenovation = v.findViewById(R.id.renovationEditText);
        goodsCleaning = v.findViewById(R.id.cleaningEditText);
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
            case R.id.housingSubmitData:

                if(checkUserInput()) {
                    try {
                        url = new URL("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/HousingCalculator?" +
                                "familySize=" + family + "&query.isPrimaryHouse=true&query.houseType=" + houseType + "&query.buildYear=" + buildYear + "&query.area=" + area + "&" +
                                "query.floorCount=" + floors + "&query.electricityConsumption=" + electricityConsumption + "&query.greenElectricityPercentage=" + greenElectricityPercentage + "&" +
                                "query.heatingMode=" + heatingType + "&query.winterHeating=off&query.districtHeatConsumption=" + districtHeatingConsumption + "&" +
                                "query.heatingOilConsumption=" + heatingOilConsumption + "&query.additionalHeatPump=" + additionalAirPumpHeating.isChecked() + "&" +
                                "query.additionalHeatSelfGenerated=" + additionalOwnElectricityHeating.isChecked() + "&" +
                                "query.additionalHeatWood=" + additionalWoodHeating.isChecked() + "&query.appliancePurchases=" + appliancePurchases + "&query.cleaningPurchases=" + cleaningPurchases + "&" +
                                "query.furniturePurchases=" + furniturePurchases + "&query.renovationPurchases=" + renovationPurchases + "&query.miscPurchases=" + miscPurchases);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    CAPI.getRequest(url);
                    assert getFragmentManager() != null;
                    getFragmentManager().popBackStack();
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

    // Checking if user input is valid
    public boolean checkUserInput(){
        if(heatingType.equals("")){
            Toast.makeText(getContext(), getResources().getString(R.string.housing_toast18) , Toast.LENGTH_SHORT).show();
            return false;
        }else if (heatingType.equals("district")){
            if(districtHeatingAmount.getText().toString().equals("")){
                Toast.makeText(getContext(), getResources().getString(R.string.housing_toast16) , Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!(districtHeatingAmount.getText().toString().equals(""))){
                districtHeatingConsumption = Integer.parseInt(districtHeatingAmount.getText().toString());
                if(districtHeatingConsumption < 0 || districtHeatingConsumption > 130000){
                    Toast.makeText(getContext(), getResources().getString(R.string.housing_toast1) , Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }else if (heatingType.equals("oil")){
            if(oilHeatingAmount.getText().toString().equals("")){
                Toast.makeText(getContext(), getResources().getString(R.string.housing_toast17) , Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!(oilHeatingAmount.getText().toString().equals(""))){
                heatingOilConsumption = Integer.parseInt(oilHeatingAmount.getText().toString());
                if(heatingOilConsumption < 0 || heatingOilConsumption > 130000){
                    Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast2), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        if ((livingSpace.getText().toString().equals(""))){
            Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast15), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!(livingSpace.getText().toString().equals(""))){
            area = Double.parseDouble(livingSpace.getText().toString());
            if(area < 10 || area > 500){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast3), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if((yearOfConstruction.getText().toString().equals(""))){
            Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast14), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!(yearOfConstruction.getText().toString().equals(""))){
            buildYear = Integer.parseInt(yearOfConstruction.getText().toString());
            if(buildYear < 1900 || buildYear > 2030){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast4), Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (!(numberOfFloors.getText().toString().equals(""))){
            floors = Integer.parseInt(numberOfFloors.getText().toString());
            if(floors < 1 || floors > 40){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast5), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!(familySize.getText().toString().equals(""))){
            family = Integer.parseInt(familySize.getText().toString());
            if(family < 1 || family > 15){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast6), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!(electricityUsage.getText().toString().equals(""))){
            electricityConsumption = Integer.parseInt(electricityUsage.getText().toString());
            if(electricityConsumption < 0 || electricityConsumption > 150000){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast7), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!(electricityGreenPercent.getText().toString().equals(""))){
            greenElectricityPercentage = Integer.parseInt(electricityGreenPercent.getText().toString());
            if(greenElectricityPercentage < 0 || greenElectricityPercentage > 100){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast8), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!(goodsAppliance.getText().toString().equals(""))){
            appliancePurchases = Integer.parseInt(goodsAppliance.getText().toString());
            if(appliancePurchases < 0 || appliancePurchases > 10000){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast9), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!(goodsFurniture.getText().toString().equals(""))){
            furniturePurchases = Integer.parseInt(goodsFurniture.getText().toString());
            if(furniturePurchases < 0 || furniturePurchases > 10000){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast10), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!(goodsFurniture.getText().toString().equals(""))){
            renovationPurchases = Integer.parseInt(goodsRenovation.getText().toString());
            if(renovationPurchases < 0 || renovationPurchases > 10000){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast11), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!(goodsFurniture.getText().toString().equals(""))){
            miscPurchases = Integer.parseInt(goodsTableware.getText().toString());
            if(miscPurchases < 0 || miscPurchases > 10000){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast12), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!(goodsCleaning.getText().toString().equals(""))){
            cleaningPurchases = Integer.parseInt(goodsFurniture.getText().toString());
            if(cleaningPurchases < 0 || cleaningPurchases > 10000){
                Toast.makeText(getContext(),  getResources().getString(R.string.housing_toast13), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}