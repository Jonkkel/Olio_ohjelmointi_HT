package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HarryPlotter {
    Calendar calendar;
    GraphView graph;

    @SuppressLint("StaticFieldLeak")
    private static HarryPlotter plotter = null; // singleton

    public static HarryPlotter getInstance() {
        if (plotter == null) {
            plotter = new HarryPlotter();
            plotter.harryPlotter();
        }
        return plotter; // return only one and same Plotter
    }

    public void harryPlotter(){
        //this.graph = graph;
        //plottingTool();
    }

    public void plottingTool(ArrayList<DatEmission> de, GraphView graph){
        LineGraphSeries<DataPoint> series;
        series = new LineGraphSeries<>();
        calendar = Calendar.getInstance();

        double y = 0;
        int listSize = de.size();
        System.out.println(listSize);

        if (listSize < 13) {
            for (int i = 0; i < listSize - 1; i++){
                calendar.setTimeInMillis(Long.parseLong(de.get(i).getTime()));
                Date d = calendar.getTime();
                System.out.println(d);
                y = de.get(i).getEmission();
                series.appendData(new DataPoint(d, y), true, 12);
            }
        } else {
            for (int i = listSize - 13; i < listSize - 1; i++){
                calendar.setTimeInMillis(Long.parseLong(de.get(i).getTime()));
                Date d = calendar.getTime();
                System.out.println(d);
                y = de.get(i).getEmission();
                series.appendData(new DataPoint(d, y), true, 12);
            }
        }
        graph.addSeries(series);
        graph.getViewport().setMinX(series.getLowestValueX());
        graph.getViewport().setMaxX(series.getHighestValueX());
        graph.getViewport().setXAxisBoundsManual(true);
    }


    public void readCSV(String fileName, GraphView graph) {

        /* This method reads the first two columns of a .csv file (name of the file is the input parameter)
        and constructs a list consisting of objects containing the time (String) and emissions (Double).
        The first column of the file will be read as the time, second column will be read as the emission data. */

        String row = "";
        ArrayList<DatEmission> dateEmissionList = new ArrayList<DatEmission>();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";"); //Splits the row into an array
                DatEmission de = new DatEmission();
                de.setDateEmission(data[0], (Double.parseDouble(data[1].replace(",", "."))));
                dateEmissionList.add(de);
            }
            csvReader.close();
            plottingTool(dateEmissionList, graph);
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Add data first.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*for (DatEmission embryo : dateEmissionList) {
            System.out.println(embryo.getYear() + " | " + embryo.getWeek() + " | " + Double.toString(embryo.getEmission()));
        }*/ // This exists for troubleshooting purposes
    }
}
