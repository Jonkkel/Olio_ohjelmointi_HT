package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Add_data_food extends Fragment implements SeekBar.OnSeekBarChangeListener{

    SeekBar beefbar, porkbar, fishbar, cheesebar, vegetablebar, dairybar, eggbar, ricebar;
    CheckBox lowCarbon;
    double beef = 0, pork = 0, fish = 0, cheese = 0, dairy = 0, rice = 0, vegetables = 0, eggs = 0;
    int restaurant = 0;
    TextView beef_amount, pork_amount, fish_amount, cheese_amount, dairy_amount, rice_amount, vegetable_amount, egg_amount;
    EditText restaurantSpendings;
    RadioGroup radioGroup;
    String diet = "";

    URL url;
    Button submitData;

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_data_food, container, false);
        CallApi CAPI = CallApi.getInstance(getActivity());
        beef_amount = (TextView) v.findViewById(R.id.beefbar_amount);
        pork_amount = (TextView) v.findViewById(R.id.pork_amount);
        fish_amount = (TextView) v.findViewById(R.id.fishbar_amount);
        cheese_amount = (TextView) v.findViewById(R.id.cheesebar_amount);
        dairy_amount = (TextView) v.findViewById(R.id.dairybar_amount);
        rice_amount = (TextView) v.findViewById(R.id.ricebar_amount);
        vegetable_amount = (TextView) v.findViewById(R.id.vegetablebar_amount);
        egg_amount = (TextView) v.findViewById(R.id.eggbar_amount);
        restaurantSpendings = (EditText) v.findViewById(R.id.restaurant_exp);

        lowCarbon = (CheckBox) v.findViewById(R.id.airpump_box);
        submitData = (Button) v.findViewById(R.id.submitFoodData);
        beefbar = (SeekBar) v.findViewById(R.id.beefBar);
        porkbar = (SeekBar) v.findViewById(R.id.porkBar);
        fishbar = (SeekBar) v.findViewById(R.id.fishBar);
        cheesebar = (SeekBar) v.findViewById(R.id.cheeseBar);
        dairybar = (SeekBar) v.findViewById(R.id.dairyBar);
        ricebar = (SeekBar) v.findViewById(R.id.riceBar);
        vegetablebar = (SeekBar) v.findViewById(R.id.vegetableBar);
        eggbar = (SeekBar) v.findViewById(R.id.eggBar);

        radioGroup = (RadioGroup) v.findViewById(R.id.diet_group);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch(checkedId){
                case R.id.radioVegan:
                    diet = "vegan";
                    break;
                case R.id.radioVegetarian:
                    diet = "vegetarian";
                    break;
                case R.id.radioOmnivore:
                    diet = "omnivore";
                    break;
            }
            System.out.println(diet);
        });

        beefbar.setOnSeekBarChangeListener(this);
        porkbar.setOnSeekBarChangeListener(this);
        fishbar.setOnSeekBarChangeListener(this);
        cheesebar.setOnSeekBarChangeListener(this);
        dairybar.setOnSeekBarChangeListener(this);
        ricebar.setOnSeekBarChangeListener(this);
        vegetablebar.setOnSeekBarChangeListener(this);
        eggbar.setOnSeekBarChangeListener(this);
        beef_amount.setText(beef + " " + v.getResources().getString(R.string.add_data_unit1));
        pork_amount.setText(pork + " " + v.getResources().getString(R.string.add_data_unit1));
        fish_amount.setText(fish + " " + v.getResources().getString(R.string.add_data_unit1));
        cheese_amount.setText(cheese + " " + v.getResources().getString(R.string.add_data_unit1));
        dairy_amount.setText(dairy + " " + v.getResources().getString(R.string.add_data_unit1));
        rice_amount.setText(rice + " " + v.getResources().getString(R.string.add_data_unit1));
        vegetable_amount.setText(vegetables + " " + v.getResources().getString(R.string.add_data_unit1));
        egg_amount.setText(eggs + " " + v.getResources().getString(R.string.add_data_unit2));

        submitData.setOnClickListener(v1 -> {
            if (diet.equals("")){
                Toast.makeText(getContext(), getString(R.string.Toast_diet), Toast.LENGTH_SHORT).show();
            }else if (!(restaurantSpendings.getText().toString().equals(""))){
                restaurant  = Integer.parseInt(restaurantSpendings.getText().toString());
                if (restaurant < 0 || restaurant > 800) {
                    Toast.makeText(getContext(), getString(R.string.Toast_restaurant), Toast.LENGTH_SHORT).show();
                }
            }else{
                try {
                    url = new URL("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?" +
                            "query.diet=" + diet + "&query.lowCarbonPreference=" + lowCarbon.isChecked() + "&query.beefLevel" +
                            "=" + Math.round(beef) + "&query.fishLevel=" + Math.round(fish) + "&query.porkPoultryLevel=" + Math.round(pork) +
                            "&query.dairyLevel=" + Math.round(dairy) + "&query.cheeseLevel=" + Math.round(cheese) + "&query.riceLevel=" +
                            Math.round(rice) + "&query.eggLevel=" + Math.round(eggs) + "&query.winterSaladLevel=" + Math.round(vegetables) +
                            "&query.restaurantSpending=" + restaurant);
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


    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
        if (!(restaurantSpendings.getText().toString().equals(""))) {
            submitData.setEnabled(true);
        }
        if (!(diet.equals(""))){
            submitData.setEnabled(true);
        }
        if(seekBar.equals(beefbar)){
            beef = (double) progress /100;
            beef_amount.setText(beef + " " + getView().getResources().getString(R.string.add_data_unit1));
            beef = (beef/0.4)*100;
        }else if(seekBar.equals(porkbar)){
            pork = (double) progress /100;
            pork_amount.setText(pork + " " + getView().getResources().getString(R.string.add_data_unit1));
            pork = (pork/1.0)*100;
        }else if(seekBar.equals(fishbar)) {
            fish = (double) progress /100;
            fish_amount.setText(fish + " " + getView().getResources().getString(R.string.add_data_unit1));
            fish = (fish/0.6)*100;
        }else if(seekBar.equals(cheesebar)){
            cheese = (double) progress /100;
            cheese_amount.setText(cheese + " " + getView().getResources().getString(R.string.add_data_unit1));
            cheese = (cheese/0.3)*100;
        }else if(seekBar.equals(dairybar)){
            dairy = (double) progress /100;
            dairy_amount.setText(dairy + " " + getView().getResources().getString(R.string.add_data_unit1));
            dairy = (dairy/3.8)*100;
        }else if(seekBar.equals(ricebar)){
            rice = (double) progress /100;
            rice_amount.setText(rice + " " + getView().getResources().getString(R.string.add_data_unit1));
            rice = (rice/0.09)*100;
        }else if(seekBar.equals(vegetablebar)){
            vegetables = (double) progress /100;
            vegetable_amount.setText(vegetables + " " + getView().getResources().getString(R.string.add_data_unit1));
            vegetables = vegetables/1.4;
        }else if(seekBar.equals(eggbar)){
            eggs = progress;
            egg_amount.setText(eggs + " " + getView().getResources().getString(R.string.add_data_unit2));
            eggs = (eggs/3.0)*100;
        }
    }

    @Override
    public void onStartTrackingTouch(android.widget.SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(android.widget.SeekBar seekBar) {

    }
}
