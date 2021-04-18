package com.example.olio_ohjelmointi_ht;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Begin_screen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

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
        if (item.getItemId() == R.id.add_data){
            fragment = new Add_data_screen();
        }else if(item.getItemId() == R.id.check_progress){
            fragment = new Progress_screen();
        }else if(item.getItemId() == R.id.home_screen){
            fragment = new Home_screen();
        }else if(item.getItemId() == R.id.statistics){
            fragment = new Statistics_screen();
        }else if(item.getItemId() == R.id.settings){
            fragment = new Settings_screen();
        }else{
            return false;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.Main_fragment, fragment);
        transaction.commit();
        return true;
    }

}
