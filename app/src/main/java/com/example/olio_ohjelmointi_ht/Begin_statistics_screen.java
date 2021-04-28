package com.example.olio_ohjelmointi_ht;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;

public class Begin_statistics_screen extends Fragment{
    GraphView graph, graph2, graph3, graph4, graph5, graph6;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics_screen, container, false);
        context = getContext();
        graph = (GraphView) v.findViewById(R.id.graph);
        graph2 = (GraphView) v.findViewById(R.id.weight_graph);
        graph3 = (GraphView) v.findViewById(R.id.exercise_graph);
        graph4 = (GraphView) v.findViewById(R.id.graph4);
        graph5 = (GraphView) v.findViewById(R.id.graph5);
        graph6 = (GraphView) v.findViewById(R.id.graph6);
        HarryPlotter plotter = HarryPlotter.getInstance();
        try {
            PathFinder path = PathFinder.getInstance(this.context);
            String pathname = path.pathBuilder();
            plotter.readCSV(pathname + "", graph);
            plotter.readCSV(pathname + "foodData.csv", graph2);
            plotter.readCSV(pathname + "housingData.csv", graph3);
            plotter.readCSV(pathname + "travelData.csv", graph4);
            plotter.readCSV(pathname + "consumptionData.csv", graph5);
            plotter.readCSV(pathname + "recyclingData.csv", graph6);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), getContext().getString(R.string.changed_username), Toast.LENGTH_SHORT).show(); // MUUTA TÄMÄ TOASTIN TEKSTI
        }
        return v;
    }
/* Voi käyttää ja kannattaa käyttää, jos on tekstikentää jonka tekstiä haluaa muokata.
    Ajetaan sen jälkeen kun fragmentti on luotu - Turvallisempi metodi kuin ylempi
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



*/
}