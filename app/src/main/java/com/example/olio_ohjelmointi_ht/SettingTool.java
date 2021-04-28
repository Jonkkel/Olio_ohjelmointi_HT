package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SettingTool {

    Context context;
    User user;

    @SuppressLint("StaticFieldLeak")
    private static SettingTool settingTool = null; // singleton

    public static SettingTool getInstance(Context con) {
        if (settingTool == null) {
            settingTool = new SettingTool();
            settingTool.Settings(con);
        }
        return settingTool; // return only one and same LogInTool
    }
    public void Settings(Context con){
        // Getting context so we can run androidstudio commands
        this.context = con;
    }

    public boolean checkUsernameAvailability(String username){
        File directory = new File(context.getFilesDir() + File.separator + username); // create a folder
        if (!directory.exists()) {
            return false;
        }else{
            return true;
        }
    }

    public boolean checkPassword(String oldPassword){
        user = getUserObject();
        if(user.getPassword().equals(LogInTool.encrypt(oldPassword+user.getName()))){
            return true;
        }
        return false;
    }

    /*public boolean checkpasswordGoodness(String newPassword){
        New_User NewUser = New_User.
    }*/
    public void test(){
        String username = user.getUsername();
        System.out.println(username);
        Integer age = user.getAge();
        System.out.println(age);
        String city = user.getCity();
        System.out.println(city);
        String email = user.getEmail();
        System.out.println(email);
    }

    public void changeUsername(String newUsername){
        String folderPath = createFolder(newUsername);
        String newFileName = folderPath + "User_Info_" + newUsername;
        userDataCopier(newFileName);

        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
            }
        }
    }

    public void changePassword(String newPassword){
        getUserInformation();
        test();
        if (user == null){
            System.out.println("Error");
        }else{
            user.setPassword(newPassword);
            saveUserInformation();
        }
    }



    public void changeUserAge(String newAge){
        getUserInformation();
        test();
        if (user == null){
            System.out.println("Error");
        }else{
            user.setAge(Integer.parseInt((newAge)));
            saveUserInformation();
        }
    }

    public void changeUserCity(String newCity){
        getUserInformation();
        test();
        if (user == null){
            System.out.println("Error");
        }else{
            user.setCity(newCity);
            saveUserInformation();
        }
    }

    public void changeUserEmail(String newEmail){
        getUserInformation();
        test();
        if (user == null){
            System.out.println("Error");
        }else{
            user.setEmail(newEmail);
            saveUserInformation();
        }
    }

    public void getUserInformation(){
        SharedPreferences prefs = context.getSharedPreferences("User", MODE_PRIVATE);
        String cUser = prefs.getString("Current User", "");
        File directory = new File(context.getFilesDir() + File.separator + cUser);
        File fileName = new File(directory,   "User_Info_" + cUser + ".txt");
        try{
            FileInputStream fIn = new FileInputStream(fileName);
            ObjectInputStream is = new ObjectInputStream(fIn);
            user = (User) is.readObject();
            fIn.close();
            is.close();
            System.out.println("directory:" + directory.toString());
            System.out.println("filename:" + fileName.toString());
            fileName.delete();
            //directory.delete();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserObject(){
        SharedPreferences prefs = context.getSharedPreferences("User", MODE_PRIVATE);
        String cUser = prefs.getString("Current User", "");
        File directory = new File(context.getFilesDir() + File.separator + cUser);
        File fileName = new File(directory,   "User_Info_" + cUser + ".txt");
        try{
            FileInputStream fIn = new FileInputStream(fileName);
            ObjectInputStream is = new ObjectInputStream(fIn);
            user = (User) is.readObject();
            fIn.close();
            is.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return user;
    }


    public void saveUserInformation(){
        File directory = new File(context.getFilesDir() + File.separator + user.getUsername()); // create a folder
        if (!directory.exists()) {
            directory.mkdir();
        }
        File newFile = new File(directory,  "User_Info_" + user.getUsername() + ".txt"); // create txt file for users information
        if(!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println(newFile.toString());
            FileOutputStream fOut =  new FileOutputStream(newFile); // write users information to file
            ObjectOutputStream outputWriter = new ObjectOutputStream(fOut);
            outputWriter.writeObject(user);
            outputWriter.close(); // close file
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = context.getSharedPreferences("User", MODE_PRIVATE).edit();
        editor.putString("Current User", user.getUsername());
        editor.apply();
    }


    public void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = context.getSharedPreferences("settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = context.getSharedPreferences("settings", MODE_PRIVATE);
        String language = prefs.getString("My_lang", "");
        setLocale(language);
    }

    public int checkLanguage(){
        int position = -1;
        SharedPreferences prefs = context.getSharedPreferences("settings", MODE_PRIVATE);
        String language = prefs.getString("My_lang", "en");
        if (language.equals("en")){
            position = 0;
        }else if (language.equals("fi")){
            position = 1;
        }else if (language.equals("sv")){
            position = 2;
        }
        return position;
    }

    public String createFolder(String folderName) {
        PathFinder path = PathFinder.getInstance(this.context);
        StringBuilder sb = new StringBuilder(path.pathBuilder());

        String newFolder = sb.replace(sb.lastIndexOf("/"), sb.length(), sb.replace(sb.lastIndexOf("/"), sb.length(), "").toString()).toString() + folderName;

        File f1 = new File(newFolder);
        boolean bool = f1.mkdir();
        if (bool) {
            System.out.println("Folder successfully created!");
        } else {
            System.out.println("Error occurred while creating a new folder.");
        }
        return newFolder;
    }

    public void userDataCopier(String fileName) {

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

            CallApi capi = new CallApi(context, "tyhjä.txt");

            int n = 0;
            int LineCount = capi.csvLineCounter(fileName); // The amount of Lines to be copied before the lastLineDataBytes is to be added

            for (int i = 1; i <= LineCount; i++) { // The for-loop keeps track of the Lines that have been written until the upper limit LineCount has been reached
                while ((n = in.read()) != '\n') { // Copies the contents of a Line into the file tempFile
                    out.write(n);
                }
                out.write('\n'); // The while-loop stops at the newline, so a newline has to be added at the end of the newly copied Line
            }

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
}
