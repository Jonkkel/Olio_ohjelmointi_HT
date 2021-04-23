package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.app.VoiceInteractor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Add_data_food extends Fragment implements SeekBar.OnSeekBarChangeListener{

    SeekBar beefbar, porkbar, fishbar, cheesebar, vegetablebar, dairybar, eggbar, ricebar;
    CheckBox lowCarbon;
    double beef = 0, pork = 0, fish = 0, cheese = 0, dairy = 0, rice = 0, vegetables = 0, eggs = 0;
    String beef1, pork1, fish1, cheese1, dairy1, rice1, vegetables1, eggs1;
    Boolean checked;
    TextView beef_amount, pork_amount, fish_amount, cheese_amount, dairy_amount, rice_amount, vegetable_amount, egg_amount;
    RadioGroup radioGroup;
    String diet;
    Button calculate;
    URL url;
    HttpURLConnection urlConnection;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_data_food, container, false);
        beef_amount = (TextView) v.findViewById(R.id.beefbar_amount);
        pork_amount = (TextView) v.findViewById(R.id.pork_amount);
        fish_amount = (TextView) v.findViewById(R.id.fishbar_amount);

        cheese_amount = (TextView) v.findViewById(R.id.cheesebar_amount);
        dairy_amount = (TextView) v.findViewById(R.id.dairybar_amount);
        rice_amount = (TextView) v.findViewById(R.id.ricebar_amount);
        vegetable_amount = (TextView) v.findViewById(R.id.vegetablebar_amount);
        egg_amount = (TextView) v.findViewById(R.id.eggbar_amount);

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
        lowCarbon = (CheckBox) v.findViewById(R.id.checkBox2);

        calculate = (Button) v.findViewById(R.id.button);

        beefbar = (SeekBar) v.findViewById(R.id.beefBar);
        porkbar = (SeekBar) v.findViewById(R.id.porkBar);
        fishbar = (SeekBar) v.findViewById(R.id.fishBar);
        cheesebar = (SeekBar) v.findViewById(R.id.cheeseBar);
        dairybar = (SeekBar) v.findViewById(R.id.dairyBar);
        ricebar = (SeekBar) v.findViewById(R.id.riceBar);
        vegetablebar = (SeekBar) v.findViewById(R.id.vegetableBar);
        eggbar = (SeekBar) v.findViewById(R.id.eggBar);


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

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(diet);
                System.out.println(lowCarbon.isChecked());
                System.out.println(beef);
                System.out.println(pork);
                System.out.println(fish);
                System.out.println(cheese);
                System.out.println(dairy);
                System.out.println(rice);
                System.out.println(vegetables);
                System.out.println(eggs);
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String URL = ("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=" + diet + "&query.lowCarbonPreference=" + lowCarbon.isChecked() + "&query.beefLevel=" + Math.round(beef) + "&query.fishLevel=" + Math.round(fish) + "&query.porkPoultryLevel=" + Math.round(pork) + "&query.dairyLevel=" + Math.round(dairy) + "&query.cheeseLevel=" + Math.round(cheese) + "&query.riceLevel=" + Math.round(rice) + "&query.eggLevel=" + Math.round(eggs) + "&query.winterSaladLevel=" + Math.round(vegetables) + "&query.restaurantSpending=" + 1);
                System.out.println(URL);
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        URL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("Rest response", response.toString());
                                String delims = "[,:]";
                                String texti[] = response.toString().split(delims);
                                for (String a: texti){
                                    System.out.println(a);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Rest response", error.toString());
                            }
                        }
                );
                requestQueue.add(objectRequest);
            }
        });

        return v;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar.equals(beefbar)){
            beef = (double) progress /10;
            beef_amount.setText(beef + " " + getView().getResources().getString(R.string.add_data_unit1));
        }else if(seekBar.equals(porkbar)){
            pork = (double) progress /10;
            pork_amount.setText(pork + " " + getView().getResources().getString(R.string.add_data_unit1));
        }else if(seekBar.equals(fishbar)) {
            fish = (double) progress /10;
            fish_amount.setText(fish + " " + getView().getResources().getString(R.string.add_data_unit1));
        }else if(seekBar.equals(cheesebar)){
            cheese = (double) progress /10;
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
            eggs = progress;
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
