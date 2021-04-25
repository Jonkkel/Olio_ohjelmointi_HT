package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CallApi {
    String readLine = null;
    HttpURLConnection connection = null;
    BufferedReader in = null;
    int responseCode = 0;

    @SuppressLint("StaticFieldLeak")
    private static CallApi CAPI = null; // singleton

    public static CallApi getInstance() {
        if (CAPI == null) {
            CAPI = new CallApi();
            CAPI.Settings();
        }
        return CAPI; // return only one and same LogInTool
    }
    public void Settings(){
        // Getting context so we can run Android Studio commands

    }

    public void getRequest(URL url){
        // https://dzone.com/articles/how-to-implement-get-and-post-request-through-simp
        // Throwing GET request to API
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("userId", "olio"); // set userId its a sample here
            responseCode = connection.getResponseCode();
        }catch (IOException e) {
            e.printStackTrace();
        }
        // reading API response to String 'response'
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try {
                assert connection != null;
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                parseString(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Problem with Get request");
        }
    }

    @SuppressLint("SdCardPath")
    public void parseString(StringBuilder response){
        System.out.println(response.toString());
        char c = response.charAt(0);
        //if(Character.isDigit(c)){
        Double d = Double.valueOf(response.toString());
        writeCSV("/data/user/0/com.example.olio_ohjelmointi_ht/files/karhu/tietoa.csv", d);
    }

    public void writeCSV(String fileName, Double emission) {

        /* This method writes emission (Double) data given as input parameter, and writes it into a .csv file alongside
        the current year and week. The data in the .csv file will be in the following form: yyyy;ww;emission\n .
        As of now, this method will not modify the .csv file in any way if the latest row added is on the current week. */

        Calendar calendar = new GregorianCalendar();
        Date today = new Date();
        calendar.setTime(today);

        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int currentYear = calendar.get(Calendar.YEAR);

        File fileExists = new File(fileName);
        if (!fileExists.isFile()) { // If no file matching fileName exists, creates one and fills accordingly
            try {
                FileWriter csvWriter = new FileWriter(fileName);
                csvWriter.write(currentYear + ";" + currentWeek + ";" + emission + "\n");
                csvWriter.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (currentWeek == timeDelta(fileName, "weekLastRow") && currentYear == timeDelta(fileName, "yearLastRow")) {
            /* If the latest emission log is from the same week as new one, changes the latest emission log into the new one.
            See editLastRow for further description */
            editLastRow(fileName, (currentYear + ";" + currentWeek + ";" + emission + "\n").getBytes());
        } else {
            int weekCounter = (timeDelta(fileName, "weekLastRow") + 1); // Sets the weekCounter to start from the first missing week in the file
            int weeksLeft = 52; // The amount of weeks in a year (if current year, will be set to currentWeek)
            try {
                FileWriter csvWriter = new FileWriter(fileName, true);
                for (int i = timeDelta(fileName, "yearLastRow"); i <= currentYear; i++) {
                    if (i == currentYear) {
                        weeksLeft = currentWeek; // Sets the remaining weeks to current week
                    }
                    for (int j = weekCounter; j <= weeksLeft; j++) { //if currentWeek == weekLastRow, this will be skipped, see weekCounter
                        csvWriter.append(i + ";" + j + ";" + emission + "\n"); // year;week;emission\n
                    }
                    if (i < currentYear) { // Resets the weekCounter if one year has passed and the year isn't current yet
                        weekCounter = 1;
                    }
                }
                csvWriter.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public int timeDelta(String fileName, String mode) {

        /* This method reads the file[fileName](String) given as an input parameter
        and returns one of the following depending on the mode(String) given as an input parameter:
        weekDelta/yearDelta - returns the difference between the current week/year and the week/year of the latest row in the file
        weekLastRow/yearLastRow - returns the week/year of the latest row in the file*/

        String row = "";
        String week = "";
        String year = "";

        Calendar calendar = new GregorianCalendar();
        Date today = new Date();
        calendar.setTime(today);

        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR); // The week number of the current week
        int currentYear = calendar.get(Calendar.YEAR); // The current year

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
            while ((row = csvReader.readLine()) != null) { // Goes through the file and sets year and week to equal the latest entry
                String[] data = row.split(";");
                year = data[0];
                week = data[1];
            }
            csvReader.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (mode.equals("yearDelta")) { // Returns the difference (in years) of the current year and the year of the latest file entry
            int delta = (currentYear - Integer.parseInt(year));
            return delta;
        } else if (mode.equals("weekDelta")) { // Returns the difference (in weeks) of the current week and the week of the latest file entry
            int delta = (currentWeek - Integer.parseInt(week));
            return delta;
        } else if (mode.equals("yearLastRow")) { // Returns the year of the latest file entry
            int current = Integer.parseInt(year);
            return current;
        } else if (mode.equals("weekLastRow")) { //Returns the week of the latest file entry
            int current = Integer.parseInt(week);
            return current;
        } else { // If this is reached, there is a typo in the second input parameter in th code calling for deltaTime
            int current = Integer.parseInt(year);
            System.out.println("TYPO IN THE 'MODE' PARAMETER IN THE CODE CALLING FOR timeDelta"); // This is a poor solution, needs something better
            return current;
        }
    }

    public void editLastRow(String fileName, byte[] lastRowDataBytes) {

        //TODO add descriptions

        StringBuilder sb = new StringBuilder(fileName);
        String tempFile = sb.replace(sb.lastIndexOf("/"), sb.length(), "/tempFile.csv").toString();


        try {
            FileInputStream in = new FileInputStream(fileName);
            FileOutputStream out = new FileOutputStream(tempFile);

            int n = 0;
            int rowCount = csvRowCounter(fileName) - 1;

            for (int i = 1; i <= rowCount; i++) {
                while ((n = in.read()) != '\n') {
                    out.write(n);
                }
                out.write('\n');
            }
            out.write(lastRowDataBytes);

            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        File fileToDelete = new File(fileName);
        File fileToRename = new File(tempFile);
        fileToDelete.delete();
        fileToRename.renameTo(fileToDelete);
    }

    private int csvRowCounter(String fileName) {

        //TODO add descriptionss

        int n = 0;
        int rowCounter = 0;

        try {
            FileInputStream in = new FileInputStream(fileName);
            while ((n = in.read()) != -1) {
                if (n == '\n') {
                    rowCounter++;
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return rowCounter;
    }
}
