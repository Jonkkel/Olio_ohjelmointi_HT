package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StrictMode;

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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // https://dzone.com/articles/how-to-implement-get-and-post-request-through-simp
        // Throwing GET request to API
        System.out.println(url);
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("userId", "oli2");
            responseCode = connection.getResponseCode();
        }catch (IOException e) {
            e.printStackTrace();
        }
        // reading API response to String 'response'
        System.out.println(responseCode);
        System.out.println(HttpURLConnection.HTTP_OK);
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
        writeCSV("/data/user/0/com.example.olio_ohjelmointi_ht/files", d);
    }

    public void writeCSV(String fileName, Double emission) {

        /* This method writes emission (Double) data given as input parameter, and writes it into a .csv file alongside
        the current year and week. The data in the .csv file will be in the following form: yyyy;ww;emission\n .
        As of now, this method will not modify the .csv file in any way if the latest line added is on the current week. */

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
                e.printStackTrace();
            }
        } else if (currentWeek == timeDelta(fileName, "weekLastLine") && currentYear == timeDelta(fileName, "yearLastLine")) {
            /* If the latest emission log is from the same week as new one, changes the latest emission log into the new one.
            See editLastLine for further description */
            editLastLine(fileName, (currentYear + ";" + currentWeek + ";" + emission + "\n").getBytes());
        } else {
            int weekCounter = (timeDelta(fileName, "weekLastLine") + 1); // Sets the weekCounter to start from the first missing week in the file
            int weeksLeft = 52; // The amount of weeks in a year (if current year, will be set to currentWeek)
            try {
                FileWriter csvWriter = new FileWriter(fileName, true);
                for (int i = timeDelta(fileName, "yearLastLine"); i <= currentYear; i++) {
                    if (i == currentYear) {
                        weeksLeft = currentWeek; // Sets the remaining weeks to current week
                    }
                    for (int j = weekCounter; j <= weeksLeft; j++) { //if currentWeek == weekLastLine, this will be skipped, see weekCounter
                        csvWriter.append(i + ";" + j + ";" + emission + "\n"); // year;week;emission\n
                    }
                    if (i < currentYear) { // Resets the weekCounter if one year has passed and the year isn't current yet
                        weekCounter = 1;
                    }
                }
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int timeDelta(String fileName, String mode) {

        /* This method reads the file[fileName](String) given as an input parameter
        and returns one of the following depending on the mode(String) given as an input parameter:
        weekDelta/yearDelta - returns the difference between the current week/year and the week/year of the latest Line in the file
        weekLastLine/yearLastLine - returns the week/year of the latest Line in the file*/

        String Line = "";
        String week = "";
        String year = "";

        Calendar calendar = new GregorianCalendar();
        Date today = new Date();
        calendar.setTime(today);

        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR); // The week number of the current week
        int currentYear = calendar.get(Calendar.YEAR); // The current year

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
            while ((Line = csvReader.readLine()) != null) { // Goes through the file and sets year and week to equal the latest entry
                String[] data = Line.split(";");
                year = data[0];
                week = data[1];
            }
            csvReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mode.equals("yearDelta")) { // Returns the difference (in years) of the current year and the year of the latest file entry
            return (currentYear - Integer.parseInt(year));
        } else if (mode.equals("weekDelta")) { // Returns the difference (in weeks) of the current week and the week of the latest file entry
            return (currentWeek - Integer.parseInt(week));
        } else if (mode.equals("yearLastLine")) { // Returns the year of the latest file entry
            return Integer.parseInt(year);
        } else if (mode.equals("weekLastLine")) { //Returns the week of the latest file entry
            return Integer.parseInt(week);
        } else { // If this is reached, there is a typo in the second input parameter in th code calling for deltaTime
            int current = Integer.parseInt(year);
            System.out.println("TYPO IN THE 'MODE' PARAMETER IN THE CODE CALLING FOR timeDelta"); // This is a poor solution, needs something better
            return current;
        }
    }

    public void editLastLine(String fileName, byte[] lastLineDataBytes) {

        /* This method edits the last Line of a file fileName(String) into byte array lastLineDataBytes(byte[]). Both of these must be given as
        input parameters. The method copies the entire file fileName into a new file tempFile, excluding the last Line of data, and then writes
        the input lastLineDataBytes into the tempFile. After the process is done the original file fileName will be deleted and the new file
        tempFile renamed fileName */

        StringBuilder sb = new StringBuilder(fileName);
        String tempFile = sb.replace(sb.lastIndexOf("/"), sb.length(), "/tempFile.csv").toString();
        // Copies the path from fileName sans the name of the file itself and then adds the ending /tempFile.csv into the path
        // "examplePath/exampleFolder/exampleFileName.csv" -> "examplePath/exampleFolder/tempFile.csv"

        try {
            FileInputStream in = new FileInputStream(fileName);
            FileOutputStream out = new FileOutputStream(tempFile);

            int n = 0;
            int LineCount = csvLineCounter(fileName) - 1; // The amount of Lines to be copied before the lastLineDataBytes is to be added

            for (int i = 1; i <= LineCount; i++) { // The for-loop keeps track of the Lines that have been written until the upper limit LineCount has been reached
                while ((n = in.read()) != '\n') { // Copies the contents of a Line into the file tempFile
                    out.write(n);
                }
                out.write('\n'); // The while-loop stops at the newline, so a newline has to be added at the end of the newly copied Line
            }
            out.write(lastLineDataBytes); // Writes the replacement new Line after all but the last Lines have been copied

            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File fileToDelete = new File(fileName);
        File fileToRename = new File(tempFile);
        if (!fileToDelete.delete()) { // Deletes the old file
            System.out.println("A problem occurred while deleting a file in editLastLine");
        }
        if (!fileToRename.renameTo(fileToDelete)) { // Renames the new file into the old file. "Editing" the file is now complete
            System.out.println("A problem occurred while renaming a file in editLastLine");
        }
    }

    private int csvLineCounter(String fileName) {

        /* This method counts all the amount of line in a (.csv) file fileName given as input. The Lines are counted by counting all the
        newline("\n")-bytes in the file. Returns the amount of Lines as int. Feel free to change this method into public if you need a line
        counter somewhere else */

        int n = 0;
        int LineCounter = 0;

        try {
            FileInputStream in = new FileInputStream(fileName);
            while ((n = in.read()) != -1) { // Goes through the whole file
                if (n == '\n') { // When a newline is encountered increases the lineCounter by one
                    LineCounter++;
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return LineCounter;
    }
}