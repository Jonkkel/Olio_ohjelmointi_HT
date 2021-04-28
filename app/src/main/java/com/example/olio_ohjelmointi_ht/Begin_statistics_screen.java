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
    GraphView graph1, graph2, graph3, graph4, graph5;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics_screen, container, false);
        context = getContext();
        graph1 = (GraphView) v.findViewById(R.id.foodGraph);
        graph2 = (GraphView) v.findViewById(R.id.housingGraph);
        graph3 = (GraphView) v.findViewById(R.id.travelGraph);
        graph4 = (GraphView) v.findViewById(R.id.consumptionGraph);
        graph5 = (GraphView) v.findViewById(R.id.recyclingGraph);
        HarryPlotter plotter = HarryPlotter.getInstance();
        try {
            PathFinder path = PathFinder.getInstance(this.context);
            String pathname = path.pathBuilder();
            plotter.readCSV(pathname + "foodData.csv", graph1);
            plotter.readCSV(pathname + "housingData.csv", graph2);
            plotter.readCSV(pathname + "travelData.csv", graph3);
            plotter.readCSV(pathname + "consumptionData.csv", graph4);
            plotter.readCSV(pathname + "recyclingData.csv", graph5);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), getContext().getString(R.string.changed_username), Toast.LENGTH_SHORT).show(); // MUUTA TÄMÄ TOASTIN TEKSTI
        }
        return v;
    }
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
*/
}