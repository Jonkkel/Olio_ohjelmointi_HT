package com.example.olio_ohjelmointi_ht;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jjoe64.graphview.GraphView;

import static android.content.Context.MODE_PRIVATE;

public class Begin_progress_screen extends Fragment implements View.OnClickListener{

    EditText exercise;
    EditText weight;
    Button exercise_button, weight_button;
    String exercise_amount;
    String weight_amount;
    Context context;
    GraphView exercise_graph, weight_graph;
    User_data_plotting UDP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_progress_screen, container, false);
        exercise = (EditText) v.findViewById(R.id.exercise);
        weight = (EditText) v.findViewById(R.id.weight);
        exercise_button = (Button) v.findViewById(R.id.submit_exercise);
        weight_button = (Button) v.findViewById(R.id.submit_weight);
        exercise_graph = (GraphView) v.findViewById(R.id.exercise_graph);
        weight_graph = (GraphView) v.findViewById(R.id.weight_graph);

        context = getContext();


        return inflater.inflate(R.layout.fragment_progress_screen, container, false);
    }
/* Voi käyttää ja kannattaa käyttää, jos on tekstikentää jonka tekstiä haluaa muokata.
    Ajetaan sen jälkeen kun fragmentti on luotu - Turvallisempi metodi kuin ylempi*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        exercise_button.setOnClickListener(this);
        weight_button.setOnClickListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_exercise:
                exercise_amount = exercise.getText().toString();
                System.out.println(exercise_amount);
                break;
            case R.id.submit_weight:
                weight_amount = weight.getText().toString();


                break;
        UDP = User_data_plotting.getInstance();
        HarryPlotter plotter = HarryPlotter.getInstance();
        SharedPreferences prefs = context.getSharedPreferences("User", MODE_PRIVATE);
        String cUser = prefs.getString("Current User", "");

        UDP.writeCSV("Exercise_data_" + cUser , Double.parseDouble(exercise_amount));
        plotter.readCSV("Exercise_data_" + cUser , exercise_graph);
    }
}