package com.begawoinc.covians;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button search_btn;
    Spinner cities;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      find view by id
        search_btn = findViewById(R.id.search_btn);
        cities = findViewById(R.id.cities);
        fab = findViewById(R.id.fab);


//      onclicklistener for fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDetails.class);
                startActivity(intent);
            }
        });

//      adding city into spinner from firebase
        List<String> citieslist = new ArrayList<String>();
        citieslist.add("Select city");        // this is default to show the title in the drop down menu
//      setting data into spinner using firebase
        citieslist.add("Delhi");
        citieslist.add("Mumbai");
        citieslist.add("Nagpur");
        ArrayAdapter<String> citydataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citieslist);
        citydataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(citydataAdapter);


//      selecting data from drop down menu
        String city = cities.getSelectedItem().toString();

        

        

    }
}