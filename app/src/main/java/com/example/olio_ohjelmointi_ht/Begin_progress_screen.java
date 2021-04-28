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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jjoe64.graphview.GraphView;

import static android.content.Context.MODE_PRIVATE;

public class Begin_progress_screen extends Fragment implements View.OnClickListener {

    EditText exercise;
    EditText weight;
    Button exercise_button, weight_button;
    double exercise_amount;
    double weight_amount;
    Context context;
    GraphView exercise_graph, weight_graph;
    User_data_plotting UDP;
    HarryPlotter plotter;
    PathFinder path;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_progress_screen, container, false);
        context = getActivity();
        plotter = HarryPlotter.getInstance();
        path = PathFinder.getInstance(context);
        UDP = User_data_plotting.getInstance();
        exercise = (EditText) v.findViewById(R.id.exercise);
        weight = (EditText) v.findViewById(R.id.weight);
        exercise_button = (Button) v.findViewById(R.id.submit_exercise);
        weight_button = (Button) v.findViewById(R.id.submit_weight);
        exercise_graph = (GraphView) v.findViewById(R.id.exercise_graph);
        weight_graph = (GraphView) v.findViewById(R.id.weight_graph);
        exercise_button.setOnClickListener(this);
        weight_button.setOnClickListener(this);

        return v;
    }

    /* Voi käyttää ja kannattaa käyttää, jos on tekstikentää jonka tekstiä haluaa muokata.
        Ajetaan sen jälkeen kun fragmentti on luotu - Turvallisempi metodi kuin ylempi*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_exercise:
                if(!(exercise.getText().toString().equals(""))) {
                    exercise_amount = Double.parseDouble(exercise.getText().toString());
                    if(exercise_amount >= 0 && exercise_amount < 1440){
                        System.out.println("testi");
                        UDP.writeCSV(path.pathBuilder() + "Exercise_data.csv", exercise_amount);
                        plotter.readCSV(path.pathBuilder() + "Exercise_data.csv", exercise_graph);
                        System.out.println(exercise_amount);
                    }else{
                        Toast.makeText(getContext(), getResources().getString(R.string.exercise_toast) , Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.submit_weight:
                if(!(weight.getText().toString().equals(""))) {
                    weight_amount = Double.parseDouble(weight.getText().toString());
                    if(weight_amount > 0 && weight_amount < 1000){
                        System.out.println("testi2");
                        System.out.println(path.pathBuilder() + "Weight_data.csv");
                        UDP.writeCSV((path.pathBuilder() + "Weight_data.csv"), weight_amount);
                        plotter.readCSV(path.pathBuilder() + "Weight_data.csv", weight_graph);
                    }else{
                        Toast.makeText(getContext(), getResources().getString(R.string.weight_toast) , Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }
}