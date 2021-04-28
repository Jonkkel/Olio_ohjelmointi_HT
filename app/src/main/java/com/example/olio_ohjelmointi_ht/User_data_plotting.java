package com.example.olio_ohjelmointi_ht;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class User_data_plotting {

    private static User_data_plotting udp = null; // singleton

    public static User_data_plotting getInstance() {
        if (udp == null) {
            udp = new User_data_plotting();
        }
        return udp; // return only one and same
    }

    Calendar calendar2;

    public void writeCSV(String fileName, Double input) {
        System.out.println(input);
        /* This method writes input (Double) data given as input parameter, and writes it into a .csv file alongside
        the time of data in milliseconds. The data in the .csv file will be in the following form: sssssssssssss;input\n .
        If new data is added on the same week as the latest log, the latest log will be overwritten with the new data. */

        Calendar calendar = new GregorianCalendar();
        Date today = new Date();
        calendar.setTime(today);
        // find current day, month and year
        int currentDay = calendar.get(Calendar.DAY_OF_YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        long milliTime = calendar.getTimeInMillis();


        File fileExists = new File(fileName);
        System.out.println("testi");
        System.out.println(fileName);
        if (!fileExists.isFile()) { // If no file matching fileName exists, creates one and fills accordingly
            System.out.println("testi2");
            try {
                System.out.println("testi3");
                FileWriter csvWriter = new FileWriter(fileName);
                //csvWriter.write(milliTime + ";"+ input + "\n");
                csvWriter.write("1614621478961;249.0\n1615621478961;200.0\n1616621478961;150.0\n1617621478961;175.0\n1618621478961;225.0\n1619621478961;125.0\n1620621478961;200.0\n1621621478961;250.0\n1622621478961;180.0\n1623621478961;160.0\n1624621478961;50.0\n1625621478961;0.0\n1626621478961;75.0\n1627621478961;175.0\n1628621478961;200.0\n1629621478961;200.0\n1630621478961;200.0\n");
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (currentDay == timeDelta(fileName, "dayLastLine") && currentYear == timeDelta(fileName, "yearLastLine") && currentMonth == timeDelta(fileName, "monthLastLine")) {
            /* If the latest emission log is from the same week as new one, changes the latest emission log into the new one.
            See editLastLine for further information */
            System.out.println("testi4");
            editLastLine(fileName, (milliTime + ";" + input + "\n").getBytes());
        } else {
            System.out.println("testi5");
            int dayCounter = (timeDelta(fileName, "dayLastLine") + 1); // Sets the weekCounter to start from the first missing week in the file
            YearMonth yearMonthObject = YearMonth.of(currentYear, currentMonth);
            int monthsCounter = timeDelta(fileName, "monthLastLine");
            int daysLeft = yearMonthObject.lengthOfMonth();
            try {
                System.out.println("testi6");
                FileWriter csvWriter = new FileWriter(fileName, true);
                for (int i = timeDelta(fileName, "yearLastLine"); i <= currentYear; i++) {
                    System.out.println("testi7");
                    for (int j = monthsCounter; j <= currentMonth; j++){
                        System.out.println("testi8");
                        if (i == currentYear && j == currentMonth) {
                            System.out.println("testi9");
                            daysLeft = currentDay; // Sets the remaining weeks to current week
                        }
                        for (int k = dayCounter; k <= daysLeft; k++) { //if currentWeek == weekLastLine, this will be skipped, see weekCounter
                            System.out.println("testi10");
                            csvWriter.append(Long.toString(milliTime) + ";" + input + "\n"); // time(in ms);emission\n
                        }
                        if (dayCounter == daysLeft){
                            System.out.println("testi11");
                            dayCounter = 1;
                        }
                    }
                    if (i < currentYear) { // Resets the weekCounter if one year has passed and the year isn't current yet
                        System.out.println("testi12");
                        monthsCounter = 1;
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
        String time = "";

        Calendar calendar = new GregorianCalendar();
        Date today = new Date();
        calendar.setTime(today);

        int currentDay = calendar.get(Calendar.DAY_OF_YEAR); // The week number of the current week
        int currentYear = calendar.get(Calendar.YEAR); // The current year

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(fileName));
            while ((Line = csvReader.readLine()) != null) { // Goes through the file and sets year and week to equal the latest entry
                String[] data = Line.split(";");
                time = data[0];
            }
            csvReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        calendar2 = new GregorianCalendar();
        calendar2.setTimeInMillis(Long.parseLong(time));
        if (mode.equals("yearDelta")) { // Returns the difference (in years) of the current year and the year of the latest file entry
            return (currentYear - calendar2.get(Calendar.YEAR));
        } else if (mode.equals("dayDelta")) { // Returns the difference (in weeks) of the current week and the week of the latest file entry
            return (currentDay - calendar2.get(Calendar.DAY_OF_YEAR));
        } else if (mode.equals("yearLastLine")) { // Returns the year of the latest file entry
            return calendar2.get(Calendar.YEAR);
        } else if (mode.equals("dayLastLine")) { //Returns the week of the latest file entry
            return calendar2.get(Calendar.DAY_OF_YEAR);
        } else if (mode.equals("monthLastLine")){
            return calendar2.get(Calendar.MONTH);
        } else { // If this is reached, there is a typo in the second input parameter in th code calling for deltaTime
            int current = calendar2.get(Calendar.YEAR);
            System.out.println("TYPO IN THE 'MODE' PARAMETER IN THE CODE CALLING FOR timeDelta"); // This is a poor solution, needs something better
            return current;
        }
    }

    public void editLastLine(String fileName, byte[] lastLineDataBytes) {

        /* This method rewrites the last Line of a file fileName(String) with the byte array lastLineDataBytes(byte[]). Both of these must be given as
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return LineCounter;
    }
}
