package com.example.olio_ohjelmointi_ht;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;
import java.net.URL;
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

    URL url;
    Button submitData;
    int clothingAmount = 0, shoesAmount = 0, paperAmount = 0, electronicAmount = 0,
            recreationAmount = 0, communicationAmount = 0, otherAmount = 0;
    CallApi CAPI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_data_consumption, container, false);
        CAPI = CallApi.getInstance(getActivity());
        clothing = (EditText) v.findViewById(R.id.clothingText);
        shoes = (EditText) v.findViewById(R.id.shoeText);
        paper = (EditText) v.findViewById(R.id.paperText);
        electronics = (EditText) v.findViewById(R.id.electronicsText);
        recreation = (EditText) v.findViewById(R.id.recreationText);
        communication = (EditText) v.findViewById(R.id.communicationsText);
        other = (EditText) v.findViewById(R.id.otherText);

        clothing_box = (CheckBox) v.findViewById(R.id.carbon_box_clothing);
        shoe_box = (CheckBox) v.findViewById(R.id.carbon_box_shoes);
        paper_box = (CheckBox) v.findViewById(R.id.carbon_box_paper);
        electronics_box = (CheckBox) v.findViewById(R.id.carbon_box_electronics);
        recreation_box = (CheckBox) v.findViewById(R.id.carbon_box_recreation);
        communication_box = (CheckBox) v.findViewById(R.id.carbon_box_phone);
        other_box = (CheckBox) v.findViewById(R.id.carbon_box_other);
        submitData = (Button) v.findViewById(R.id.consumptionSubmitData);

        submitData.setOnClickListener(c -> {

            // Checking if user has given all the needed values and the value is in correct range.
            if(checkUserInput()){
                try {
                    // Api url address
                    url = new URL("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/" +
                            "ConsumptionCalculator?query.clothing=" + clothingAmount + "&" +
                            "query.clothingLowCarbon=" + clothing_box.isChecked() + "&" +
                            "query.communications=" + shoesAmount + "&query.communicationsLowCarbon=" + communication_box.isChecked() + "&" +
                            "query.electronics=" + electronicAmount + "&query.electronicsLowCarbon=" + electronics_box.isChecked() + "&" +
                            "query.other=" + otherAmount + "&query.otherLowCarbon=" + other_box.isChecked() + "&query.paper=" + paperAmount + "&" +
                            "query.paperLowCarbon=" + paper_box.isChecked() + "&query.recreation=" + recreationAmount + "&" +
                            "query.recreationLowCarbon=" + recreation_box.isChecked() + "&query.shoes=" + shoesAmount + "&" +
                            "query.shoesLowCarbon=" + shoe_box.isChecked());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                CAPI.getRequest(url);
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }
        });
        return v;
    }


    // If EditText is empty it is considered as value 0
    public boolean checkUserInput(){
        if((!clothing.getText().toString().equals(""))){
            clothingAmount = Integer.parseInt(clothing.getText().toString());
            if(clothingAmount < 0 || clothingAmount > 1000){
                Toast.makeText(getContext(), "The field Clothing must be between 0 and 1000.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(shoes.getText().toString().equals(""))){
            shoesAmount = Integer.parseInt(shoes.getText().toString());
            if(shoesAmount < 0 || shoesAmount > 1000) {
                Toast.makeText(getContext(), "The field Shoes must be between 0 and 1000.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(paper.getText().toString().equals(""))){
            paperAmount = Integer.parseInt(paper.getText().toString());
            if(paperAmount < 0 || paperAmount > 1000){
                Toast.makeText(getContext(), "The field Paper must be between 0 and 1000.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(electronics.getText().toString().equals(""))){
            electronicAmount = Integer.parseInt(electronics.getText().toString());
            if(electronicAmount < 0 || electronicAmount > 1000){
                Toast.makeText(getContext(), "The field Electronics must be between 0 and 1000.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(recreation.getText().toString().equals(""))){
            recreationAmount = Integer.parseInt(recreation.getText().toString());
            if(recreationAmount < 0 || recreationAmount > 5000){
                Toast.makeText(getContext(), "The field Recreation must be between 0 and 5000.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(communication.getText().toString().equals(""))){
            communicationAmount = Integer.parseInt(communication.getText().toString());
            if(communicationAmount < 0 || communicationAmount > 1000){
                Toast.makeText(getContext(), "The field Communications must be between 0 and 1000.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!(other.getText().toString().equals(""))){
            otherAmount = Integer.parseInt(other.getText().toString());
            if(otherAmount < 0 || otherAmount > 3000){
                Toast.makeText(getContext(), "The field Other must be between 0 and 3000.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
