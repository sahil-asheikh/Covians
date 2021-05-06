package com.begawoinc.covians;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddDetails extends AppCompatActivity {

    Spinner sources, cities;
    EditText hospitalClinicName, contactNo, address, otherDetails, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        sources = findViewById(R.id.sources);
        cities = findViewById(R.id.cities);
        hospitalClinicName = findViewById(R.id.hospitalClinicName);
        contactNo = findViewById(R.id.contactNo);
        address = findViewById(R.id.address);
        otherDetails = findViewById(R.id.otherDetails);
        note = findViewById(R.id.note);


//      adding resources into spinner
        List<String> resourceslist = new ArrayList<String>();
        resourceslist.add("Select Resource");        // this is default to show the title in the drop down menu
//      setting data into spinner using firebase
        resourceslist.add("Bed");
        resourceslist.add("Oxygen Cylinder");
        resourceslist.add("Plasma");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, resourceslist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sources.setAdapter(dataAdapter);


//      adding city into spinner
        List<String> citieslist = new ArrayList<String>();
        citieslist.add("Select city");        // this is default to show the title in the drop down menu
//      setting data into spinner using firebase
        citieslist.add("Delhi");
        citieslist.add("Mumbai");
        citieslist.add("Nagpur");
        ArrayAdapter<String> citydataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, citieslist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(citydataAdapter);


//      fetch data from text fields
        String resource = sources.getSelectedItem().toString();
        String hospitalName = hospitalClinicName.getText().toString();
        String number = contactNo.getText().toString();
        String hospitalAddress = address.getText().toString();
        String city = cities.getSelectedItem().toString();
        String additionalDetails = otherDetails.getText().toString();
        String extraNote = note.getText().toString();


//      firebase codes


    }
}