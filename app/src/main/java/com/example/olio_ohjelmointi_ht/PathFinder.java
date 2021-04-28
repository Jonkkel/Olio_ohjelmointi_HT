package com.example.olio_ohjelmointi_ht;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.Serializable;

/* The sole purpose of this class is to provide a method that returns a path to the user files directory. */

public class PathFinder implements Serializable {


    //String log_in_message;
    //String result;
    Context c;

    @SuppressLint("StaticFieldLeak")
    private static PathFinder PF = null; // singleton

    public static PathFinder getInstance(Context context) {
        if (PF == null) {
            PF = new PathFinder();
            PF.pathFinder(context);
        }
        return PF; // return only one and same LogInTool
    }

    public void pathFinder(Context context) {
        this.c = context;
    }

    public String pathBuilder() {

        /* This method, when called, provides direct path to user file directory as a return value, in String form */

        SharedPreferences prefs = c.getSharedPreferences("User", Context.MODE_PRIVATE);
        String userName  = prefs.getString("Current User", ""); // Fetches the name of the current user

        String pathToData = c.getFilesDir() + "/" + userName + "/";
        // /this/is/an/example/path/user/ <- adds an extra slash to the end of the path so it can be completed simply by adding the name of the wanted file.

        return pathToData;
    }
}