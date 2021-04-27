package com.example.olio_ohjelmointi_ht;

public class DatEmission {

    /* This class is very straightforward; it contains the attributes
    year(String), week(String) and emission(Double), as well as methods
    getYear, getWeek, getEmission.*/

    public String year;
    public String week;
    public Double emission;

    public void setDateEmission(String y, String w, Double e) {
        year = y;
        week = w;
        emission = e;
    }

    public String getYear() {
        return year;
    }

    public String getWeek() {
        return week;
    }

    public Double getEmission() {
        return emission;
    }

}