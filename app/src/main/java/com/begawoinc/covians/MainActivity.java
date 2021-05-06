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
    Spinner location;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      find view by id
        search_btn = findViewById(R.id.search_btn);
        location = findViewById(R.id.location);
        fab = findViewById(R.id.fab);


//      onclicklistener for fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDetails.class);
                startActivity(intent);
            }
        });

//      adding locations into sninner
        List<String> list = new ArrayList<String>();
        list.add("Select City");        // this is default to show the title in the drop down menu

//      setting data into sninner using firebase
        list.add("Delhi");
        list.add("Mumbai");
        list.add("Nagpur");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(dataAdapter);


//      selecting data from drop down menu
        String location_str = location.getSelectedItem().toString();

        

        

    }
}