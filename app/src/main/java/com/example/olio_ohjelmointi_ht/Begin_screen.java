package com.example.olio_ohjelmointi_ht;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Begin_screen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.begin_screen);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView);

        // Set homescreen active on startup
        MenuItem item = bottomNavigationView.getMenu().getItem(2);
        bottomNavigationView.setSelectedItemId(item.getItemId());
        bottomNavigationView.setOnNavigationItemSelectedListener(item1 ->{
            onNavigationItemSelected(item1);
            return true;
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_data){

        }else if(item.getItemId() == R.id.check_progress){

        }else if(item.getItemId() == R.id.home_screen){

        }else if(item.getItemId() == R.id.statistics){

        }else if(item.getItemId() == R.id.settings){

        }else{
            return false;
        }
        return true;
    }
}
