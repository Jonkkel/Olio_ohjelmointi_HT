package com.example.olio_ohjelmointi_ht;

public class DatEmission {

    /* This class is very straightforward; it contains the attributes
    time(String) and emission(Double), as well as methods
    getTime, getEmission.*/

    public String time;
    public Double emission;

    public void setDateEmission(String t, Double e) {
        time = t;
        emission = e;
    }

    public String getTime() {
        return time;
    }

    public Double getEmission() {
        return emission;
    }

}