package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.lang.invoke.ConstantCallSite;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class Add_data_travel extends Fragment implements View.OnClickListener {

    ConstraintLayout carView, publicView, flightView, mView;

    // For car and motorcycle
    EditText car_distance, passengers, moped_distance, moped_consumption;
    Spinner car_size, car_year, car_fuel;
    CheckBox shared_car;

    // For public transportation
    EditText bus_distance, train_distance, tram_distance, subway_distance, longBus_distance, longTrain_distance;

    // For boat trips and flights
    EditText boat_trip1, boat_trip2, boat_trip3, flight_fin, flight_eu, flight_canarian, flight_continental;

    Button submitData, carButton, publicButton, flightButton;

    String carSize, carFuel;
    int carYear, motorcycleDriveDist = 0, busDist = 0, trainDist = 0, tramDist = 0, subwayDist = 0, longBusDist = 0, longTrainDist = 0, driveDist = 0;
    double motorcycleConsumption = 0, passengerCount = 1;

    int TallinBoat = 0, StockBoat = 0, TraveBoat = 0, FinlandFlight = 0, EuropeFlight = 0, CanaryFlight = 0, ContinentalFlight = 0;


    CallApi CAPI;
    double carData, travelData;
    URL url;
    String cUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_data_travel, container, false);
        CAPI = CallApi.getInstance(getActivity());
        carView = v.findViewById(R.id.car_Layout);
        publicView = v.findViewById(R.id.public_Layout);
        flightView = v.findViewById(R.id.flight_Layout);
        mView = v.findViewById(R.id.travel_Layout);

        carButton = v.findViewById(R.id.car_Button);
        publicButton = v.findViewById(R.id.public_Button);
        flightButton = v.findViewById(R.id.flight_Button);

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

        submitData = v.findViewById(R.id.travelSubmitData);
        submitData.setOnClickListener(this);

        carButton.setOnClickListener(this);
        publicButton.setOnClickListener(this);
        flightButton.setOnClickListener(this);

        return v;
    }

    @SuppressLint({"NonConstantResourceId", "SdCardPath"})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_Button:
                if (carView.getVisibility() == View.GONE) {
                    closeOpenLayouts();
                    TransitionManager.beginDelayedTransition(mView, new AutoTransition());
                    carButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                    carView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mView, new AutoTransition());
                    carButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                    carView.setVisibility(View.GONE);
                }
                break;
            case R.id.public_Button:
                if (publicView.getVisibility() == View.GONE) {
                    closeOpenLayouts();
                    TransitionManager.beginDelayedTransition(mView, new AutoTransition());
                    publicButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                    publicView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mView, new AutoTransition());
                    publicButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                    publicView.setVisibility(View.GONE);
                }
                break;
            case R.id.flight_Button:
                if (flightView.getVisibility() == View.GONE) {
                    closeOpenLayouts();
                    TransitionManager.beginDelayedTransition(mView, new AutoTransition());
                    flightButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                    flightView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mView, new AutoTransition());
                    flightButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                    flightView.setVisibility(View.GONE);
                }
                break;
            case R.id.travelSubmitData:
                if(checkUserInput()){
                    getValues();
                    try {
                        url = new URL("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1" +
                                "/TransportCalculator/CarEstimate?query.detailsAdded=false&" +
                                "query.fuelDetailsAdded=false&query.buildYear=" + carYear + "&" +
                                "query.driveDistance=" + driveDist + "&query.shared=" + shared_car.isChecked() + "&query.size=" + carSize + "&" +
                                "query.fuel=" + carFuel + "&query.passengerCount=" + passengerCount );
                        carData = CAPI.getRequestReturnDouble(url);

                        url = new URL("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1" +
                                "/TransportCalculator?query.motorcycleFuelConsumption=" + motorcycleConsumption + "&" +
                                "query.motorcycleDistance=" + motorcycleDriveDist + "&query.cityBusDistance=" + busDist + "&" +
                                "query.cityTrainDistance=" + trainDist + "&query.busDistance=" + longBusDist + "&query.trainDistance=" + trainDist + "&" +
                                "query.metroDistance=" + subwayDist + "&query.tramDistance=" + tramDist + "&query.canaryFlights=" + CanaryFlight + "&" +
                                "query.europeanFlights=" + EuropeFlight + "&query.finlandFlights=" + FinlandFlight + "&query.transContinentalFlights=" + ContinentalFlight + "&" +
                                "query.germanyCruises=" + TraveBoat + "&query.estoniaCruises=" + TallinBoat + "&query.swedenCruises=" + StockBoat);
                        travelData = CAPI.getRequestReturnDouble(url);
                        SharedPreferences prefs = getActivity().getSharedPreferences("User", MODE_PRIVATE);
                        cUser = prefs.getString("Current User", "");

                        CAPI.writeCSV("/data/user/0/com.example.olio_ohjelmointi_ht/files/" + cUser + "/tiedot.csv", carData+travelData );
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    assert getFragmentManager() != null;
                    getFragmentManager().popBackStack();
                }
                break;
        }
    }

    public void closeOpenLayouts() {
        if (carView.getVisibility() == View.VISIBLE) {
            carView.setVisibility(View.GONE);
            carButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }
        if (publicButton.getVisibility() == View.VISIBLE) {
            publicView.setVisibility(View.GONE);
            publicButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }
        if (flightView.getVisibility() == View.VISIBLE) {
            flightView.setVisibility(View.GONE);
            flightButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }
    }

    public void getValues(){
        if (car_size.getSelectedItem().toString().equals("Mini")){
            carSize = "mini";
        }else if (car_size.getSelectedItem().toString().equals("Small")){
            carSize = "small";
        }else if (car_size.getSelectedItem().toString().equals("Small family")){
            carSize = "smallFamily";
        }else if (car_size.getSelectedItem().toString().equals("Medium family")){
            carSize = "mediumFamily";
        }else if (car_size.getSelectedItem().toString().equals("Large family")){
            carSize = "largeFamily";
        }else if (car_size.getSelectedItem().toString().equals("Large")){
            carSize = "large";
        }

        if (car_year.getSelectedItem().toString().equals("2003 or earlier")){
            carYear = 2003;
        }else if (car_year.getSelectedItem().toString().equals("2013 or later")){
            carYear = 2013;
        }else{
            carYear = Integer.parseInt(car_year.getSelectedItem().toString());
        }

        if (car_fuel.getSelectedItem().toString().equals("Gasoline")){
            carFuel = "gasoline";
        }else if (car_fuel.getSelectedItem().toString().equals("Diesel")){
            carFuel = "diesel";
        }else if (car_fuel.getSelectedItem().toString().equals("Ethanol mix (RE85)")){
            carFuel = "ethanolMix";
        }else if (car_fuel.getSelectedItem().toString().equals("Electricity")){
            carFuel = "electricity";
        }else if (car_fuel.getSelectedItem().toString().equals("Green Electricity")){
            carFuel = "greenElectricity";
        }else if (car_fuel.getSelectedItem().toString().equals("Bio gas")){
            carFuel = "bioGas";
        }else if (car_fuel.getSelectedItem().toString().equals("Natural gas")){
            carFuel = "groundGas";
        }
    }

    // If EditText is empty it is considered as value 0
    public boolean checkUserInput(){
        if((!moped_consumption.getText().toString().equals(""))){
            motorcycleConsumption = Double.parseDouble(moped_consumption.getText().toString());
            if(motorcycleConsumption < 0 || motorcycleConsumption > 1000){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast1), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(moped_distance.getText().toString().equals(""))){
            motorcycleDriveDist = Integer.parseInt(moped_distance.getText().toString());
            if(motorcycleDriveDist < 0 || motorcycleDriveDist > 60000) {
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast2), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(car_distance.getText().toString().equals(""))){
            driveDist = Integer.parseInt(car_distance.getText().toString());
            if(driveDist < 0 || driveDist > 120000){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast3), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(passengers.getText().toString().equals(""))){
            passengerCount = Double.parseDouble(passengers.getText().toString());
            if(passengerCount < 1 || passengerCount > 10){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast4), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(bus_distance.getText().toString().equals(""))){
            busDist = Integer.parseInt(bus_distance.getText().toString());
            if(busDist < 0 || busDist > 1000){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast5), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(train_distance.getText().toString().equals(""))){
            trainDist = Integer.parseInt(train_distance.getText().toString());
            if(trainDist < 0 || trainDist > 1000){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast6), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(tram_distance.getText().toString().equals(""))){
            tramDist = Integer.parseInt(tram_distance.getText().toString());
            if(tramDist < 0 || tramDist > 1000){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast7), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(subway_distance.getText().toString().equals(""))){
            subwayDist  = Integer.parseInt(subway_distance.getText().toString());
            if(subwayDist < 0 || subwayDist > 1000){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast8), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(longBus_distance.getText().toString().equals(""))){
            longBusDist  = Integer.parseInt(longBus_distance.getText().toString());
            if(longBusDist < 0 || longBusDist > 100000){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast9), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(longTrain_distance.getText().toString().equals(""))){
            longTrainDist  = Integer.parseInt(longTrain_distance.getText().toString());
            if(longTrainDist < 0 || longTrainDist > 100000){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast10), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(boat_trip1.getText().toString().equals(""))){
            TallinBoat = Integer.parseInt(boat_trip1.getText().toString());
            if(TallinBoat < 0 || TallinBoat > 50){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast11), Toast.LENGTH_SHORT).show();
                return false;
            }
        }if(!(boat_trip3.getText().toString().equals(""))){
            StockBoat = Integer.parseInt(boat_trip3.getText().toString());
            if(StockBoat < 0 || StockBoat > 50){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast12), Toast.LENGTH_SHORT).show();
                return false;
            }
        }if(!(boat_trip2.getText().toString().equals(""))){
            TraveBoat = Integer.parseInt(boat_trip2.getText().toString());
            if(TraveBoat < 0 || TraveBoat > 50){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast13), Toast.LENGTH_SHORT).show();
                return false;
            }
        }if(!(flight_fin.getText().toString().equals(""))){
            FinlandFlight = Integer.parseInt(flight_fin.getText().toString());
            if(FinlandFlight < 0 || FinlandFlight > 50){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast14), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(flight_eu.getText().toString().equals(""))){
            EuropeFlight = Integer.parseInt(flight_eu.getText().toString());
            if(EuropeFlight < 0 || EuropeFlight > 50){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast15), Toast.LENGTH_SHORT).show();
                return false;
            }
        }if(!(flight_canarian.getText().toString().equals(""))){
            CanaryFlight = Integer.parseInt(flight_canarian.getText().toString());
            if(CanaryFlight < 0 || CanaryFlight > 50){
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast16), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(flight_continental.getText().toString().equals(""))){
        ContinentalFlight = Integer.parseInt(flight_continental.getText().toString());
            if(ContinentalFlight < 0 || ContinentalFlight > 50) {
                Toast.makeText(getContext(), getResources().getString(R.string.add_travel_toast17), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
