package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Spinner;


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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class Add_data_waste extends Fragment {

    Spinner biowaste, carton, electronic, glass, hazardous, metal, paper, plastic, wasteAmount;
    String biowasteS, cartonS, electronicS, glassS, hazardousS, metalS, paperS, plasticS, wasteAmountS;
    URL url;
    Button submitData;
    CallApi CAPI;


    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_data_waste, container, false);
        Context con = getActivity();
        CAPI = CallApi.getInstance(con);
        biowaste = (Spinner) v.findViewById(R.id.bioSpinner);
        carton = (Spinner) v.findViewById(R.id.cartonSpinner);
        electronic = (Spinner) v.findViewById(R.id.electronicSpinner);
        glass = (Spinner) v.findViewById(R.id.glassSpinner);
        hazardous = (Spinner) v.findViewById(R.id.hazardousSpinner);
        metal = (Spinner) v.findViewById(R.id.metalSpinner);
        paper = (Spinner) v.findViewById(R.id.paperSpinner);
        plastic = (Spinner) v.findViewById(R.id.plasticSpinner);
        wasteAmount = (Spinner) v.findViewById(R.id.totalWasteSpinner);
        submitData = (Button) v.findViewById(R.id.submitWasteData);

        submitData.setOnClickListener(v1 -> {

            // Saving spinner values to strings
            biowasteS = biowaste.getSelectedItem().toString();
            cartonS = carton.getSelectedItem().toString();
            electronicS = electronic.getSelectedItem().toString();
            glassS = glass.getSelectedItem().toString();
            hazardousS = hazardous.getSelectedItem().toString();
            metalS = metal.getSelectedItem().toString();
            paperS = paper.getSelectedItem().toString();
            plasticS = plastic.getSelectedItem().toString();
            wasteAmountS = wasteAmount.getSelectedItem().toString();

            // Needed for accessing internet

            try {
                // Api url address
                url = new URL("https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/WasteCalculator?" +
                        "query.bioWaste=" + biowasteS + "&query.carton=" + cartonS + "&query.electronic=" + electronicS + "&query.glass=" + glassS +
                        "&query." + "hazardous=" + hazardousS + "&query.metal=" + metalS + "&query.paper=" + paperS + "&query.plastic=" + plasticS + "&query.amountEstimate=" + wasteAmountS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            CAPI.getRequest(url);
        });
        return v;
    }
}
