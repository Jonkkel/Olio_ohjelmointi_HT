package com.example.olio_ohjelmointi_ht;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Begin_home_screen extends Fragment implements View.OnClickListener{

    TextView welcome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context c = getActivity();
        final View v = inflater.inflate(R.layout.fragment_home_screen, container, false);
        SharedPreferences prefs = c.getSharedPreferences("User", Context.MODE_PRIVATE);
        String username = prefs.getString("Current User", "");
        welcome = (TextView) v.findViewById(R.id.welcome_back);
        System.out.println(c.getString(R.string.home_welcome) + username);
        welcome.setText(c.getString(R.string.home_welcome) + " " + username); // KATO MIKSI EI TOIMI
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }
/* Voi käyttää ja kannattaa käyttää, jos on tekstikentää jonka tekstiä haluaa muokata.
    Ajetaan sen jälkeen kun fragmentti on luotu - Turvallisempi metodi kuin ylempi
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void getUsername(String username){
        this.username= username;
    }
 */
    @Override
    public void onClick(View v) {

    }
}


