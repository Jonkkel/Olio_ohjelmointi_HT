package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HarryPlotter {

    GraphView graph;

    @SuppressLint("StaticFieldLeak")
    private static HarryPlotter plotter = null; // singleton

    public static HarryPlotter getInstance(GraphView graph) {
        if (plotter == null) {
            plotter = new HarryPlotter();
            plotter.harryPlotter(graph);
        }
        return plotter; // return only one and same LogInTool
    }

    public void harryPlotter(GraphView graph){
        this.graph = graph;
        plottingTool();
    }

    public void plottingTool(){
        LineGraphSeries<DataPoint> series;
        series = new LineGraphSeries<>();
        double x = -0.1;
        double y = 0;
        for (int i = 0; i < 1000; i++) {
            x = x + 0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x, y), true, 1000);
        }
        graph.addSeries(series);
        graph.getViewport().setMinX(series.getLowestValueX());
        graph.getViewport().setMaxX(series.getHighestValueX());
        graph.getViewport().setXAxisBoundsManual(true);

    }


    public void readCSV(String fileName) {

        /* This method reads the first two columns of a .csv file (name of the file is the input parameter)
        and constructs a list consisting of objects containing the date (String) and emissions (Double).
        The first column of the file will be read as the date, second column will be read as the emission data. */

        String row = "";
        ArrayList<DatEmission> dateEmissionList = new ArrayList<DatEmission>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";"); //Splits the row into an array
                DatEmission de = new DatEmission();
                de.setDateEmission(data[0], data[1], (Double.parseDouble(data[2].replace(",", ".")))); //The first and second
                dateEmissionList.add(de);
            }
            csvReader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (DatEmission embryo : dateEmissionList) {
            System.out.println(embryo.getYear() + " | " + embryo.getWeek() + " | " + Double.toString(embryo.getEmission()));
        }
    }


}
