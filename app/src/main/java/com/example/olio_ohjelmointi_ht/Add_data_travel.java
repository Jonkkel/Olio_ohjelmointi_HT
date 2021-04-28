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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.lang.invoke.ConstantCallSite;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class Add_data_travel extends Fragment implements View.OnClickListener {

    ConstraintLayout homeView, heatingView, goodsView, mainView;

    // For car and motorcycle
    EditText car_distance, passengers, moped_distance, moped_consumption;
    Spinner car_size, car_year, car_fuel;
    CheckBox shared_car;

    // For public transportation
    EditText bus_distance, train_distance, tram_distance, subway_distance, longBus_distance, longTrain_distance;

    // For boat trips and flights
    EditText boat_trip1, boat_trip2, boat_trip3, flight_fin, flight_eu, flight_canarian, flight_continental;

    Button submitData, homeButton, heatingButton, goodsButton;

    String carSize, carFuel;
    int carYear,motorcycleDriveDist, busDist, trainDist, tramDist, subwayDist, longBusDist, longTrainDist, driveDist;
    double motorcycleConsumption, passengerCount;

    int TallinBoat, StockBoat, TraveBoat, FinlandFlight, EuropeFlight, CanaryFlight, ContinentalFlight;

    CallApi CAPI;
    double carData, travelData;
    URL url;
    String cUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_data_travel, container, false);
        CAPI = CallApi.getInstance(getActivity());
        homeView = v.findViewById(R.id.Home_layout);
        heatingView = v.findViewById(R.id.Heating_layout);
        goodsView = v.findViewById(R.id.Goods_layout);
        mainView = v.findViewById(R.id.Main_layout);

        homeButton = v.findViewById(R.id.Home_button);
        heatingButton = v.findViewById(R.id.Heating_button);
        goodsButton = v.findViewById(R.id.Goods_button);

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

        return v;
    }

    @SuppressLint({"NonConstantResourceId", "SdCardPath"})
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
            case R.id.travelSubmitData:
                motorcycleConsumption = Double.parseDouble(moped_consumption.getText().toString());
                motorcycleDriveDist = Integer.parseInt(moped_distance.getText().toString());

                driveDist = Integer.parseInt(car_distance.getText().toString());
                passengerCount = Double.parseDouble(passengers.getText().toString());

                shared_car.isChecked();
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

                busDist = Integer.parseInt(bus_distance.getText().toString());
                trainDist = Integer.parseInt(train_distance.getText().toString());
                tramDist = Integer.parseInt(tram_distance.getText().toString());
                subwayDist  = Integer.parseInt(subway_distance.getText().toString());
                longBusDist  = Integer.parseInt(longBus_distance.getText().toString());
                longTrainDist  = Integer.parseInt(longTrain_distance.getText().toString());

                TallinBoat = Integer.parseInt(boat_trip1.getText().toString());
                StockBoat = Integer.parseInt(boat_trip3.getText().toString());
                TraveBoat = Integer.parseInt(boat_trip2.getText().toString());
                FinlandFlight = Integer.parseInt(flight_fin.getText().toString());
                EuropeFlight = Integer.parseInt(flight_eu.getText().toString());
                CanaryFlight = Integer.parseInt(flight_canarian.getText().toString());
                ContinentalFlight = Integer.parseInt(flight_continental.getText().toString());
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
        if (goodsView.getVisibility() == View.VISIBLE) {
            goodsView.setVisibility(View.GONE);
            goodsButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }
    }
}
