package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
            case R.id.Second_home_button:
                if (secondHomeView.getVisibility() == View.GONE) {
                    closeOpenLayouts();
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    secondHomeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                    secondHomeView.setVisibility(View.VISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    secondHomeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
                    secondHomeView.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void closeOpenLayouts(){
        if( homeView.getVisibility() == View.VISIBLE) {
            homeView.setVisibility(View.GONE);
            homeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }if( heatingView.getVisibility() == View.VISIBLE) {
            heatingView.setVisibility(View.GONE);
            heatingButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }if( electricityView.getVisibility() == View.VISIBLE) {
            electricityView.setVisibility(View.GONE);
            electricityButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }if( goodsView.getVisibility() == View.VISIBLE) {
            goodsView.setVisibility(View.GONE);
            goodsButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
        }if( secondHomeView.getVisibility() == View.VISIBLE){
            secondHomeView.setVisibility(View.GONE);
            secondHomeButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_arrow_down_24, 0);
    }}
}