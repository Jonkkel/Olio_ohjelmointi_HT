package com.example.olio_ohjelmointi_ht;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Begin extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private long pressedTime;
    MenuItem lastItem = null;
    BottomNavigationView bottomNavigationView;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.begin_screen);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView);

        // Set homescreen active on startup
        MenuItem item = bottomNavigationView.getMenu().getItem(2);
        onNavigationItemSelected(item);

        //https://stackoverflow.com/questions/52720871/how-to-create-a-listener-for-bottom-navigation-and-navigation-drawer-in-the-same
        bottomNavigationView.setSelectedItemId(item.getItemId());
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        // If any button has beenpressed then lets set it back to enabled
        if(lastItem != null){
            lastItem.setEnabled(true);
        }
        if (item.getItemId() == R.id.add_data){
            fragment = new Begin_add_data_screen();
        }else if(item.getItemId() == R.id.check_progress){
            fragment = new Begin_progress_screen();
        }else if(item.getItemId() == R.id.home_screen){
            fragment = new Begin_home_screen();
        }else if(item.getItemId() == R.id.statistics){
            fragment = new Begin_statistics_screen();
        }else if(item.getItemId() == R.id.settings){
            fragment = new Begin_settings_screen();
        }else{
            return false;
        }
        lastItem = item;
        item.setEnabled(false);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.Main_fragment, fragment, "TAG");
        transaction.commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            this.moveTaskToBack(true);
            this.finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }


}
