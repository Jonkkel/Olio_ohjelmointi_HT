package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

public class Add_data_housing extends Fragment implements View.OnClickListener {

    Button homeButton, electricityButton, heatingButton, goodsButton, secondHomeButton;
    ConstraintLayout mainView, homeView, heatingView, electricityView, goodsView, secondHomeView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_data_housing, container, false);

        homeView = v.findViewById(R.id.Home_layout);
        heatingView = v.findViewById(R.id.Heating_layout);
        electricityView = v.findViewById(R.id.Electricity_layout);
        goodsView = v.findViewById(R.id.Goods_layout);
        secondHomeView = v.findViewById(R.id.Second_home_layout);
        mainView = v.findViewById(R.id.Main_layout);

        homeButton = v.findViewById(R.id.Home_button);
        heatingButton = v.findViewById(R.id.Heating_button);
        electricityButton = v.findViewById(R.id.Electricity_button);
        goodsButton = v.findViewById(R.id.Goods_button);
        secondHomeButton = v.findViewById(R.id.Second_home_button);

        homeButton.setOnClickListener(this);
        heatingButton.setOnClickListener(this);
        electricityButton.setOnClickListener(this);
        goodsButton.setOnClickListener(this);
        secondHomeButton.setOnClickListener(this);
        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Home_button:
                if (homeView.getVisibility() == View.GONE) {
                    closeOpenLayout();
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    homeView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    homeView.setVisibility(View.GONE);
                }
                break;
            case R.id.Heating_button:
                if (heatingView.getVisibility() == View.GONE) {
                    closeOpenLayout();
                    System.out.println("paskaas");
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    heatingView.setVisibility(View.VISIBLE);
                } else {
                    System.out.println("paskaas212");
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    heatingView.setVisibility(View.GONE);
                }
                break;
            case R.id.Electricity_button:
                if (electricityView.getVisibility() == View.GONE) {
                    closeOpenLayout();
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    electricityView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    electricityView.setVisibility(View.GONE);
                }
                break;
            case R.id.Goods_button:
                if (goodsView.getVisibility() == View.GONE) {
                    closeOpenLayout();
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    goodsView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    goodsView.setVisibility(View.GONE);
                }
                break;
            case R.id.Second_home_button:
                if (secondHomeView.getVisibility() == View.GONE) {
                    closeOpenLayout();
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    secondHomeView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    secondHomeView.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void closeOpenLayout(){
        if( homeView.getVisibility() == View.VISIBLE)
            homeView.setVisibility(View.GONE);
        if( heatingView.getVisibility() == View.VISIBLE)
            heatingView.setVisibility(View.GONE);
        if( electricityView.getVisibility() == View.VISIBLE)
            electricityView.setVisibility(View.GONE);
        if( goodsView.getVisibility() == View.VISIBLE)
            goodsView.setVisibility(View.GONE);
        if( secondHomeView.getVisibility() == View.VISIBLE)
            secondHomeView.setVisibility(View.GONE);
    }
}