package com.begawoinc.covians;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.begawoinc.covians.modal.Leads;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddDetails extends AppCompatActivity {

    Spinner sources, cities;
    EditText hospitalClinicName, contactNo, address, otherDetails, note;
    FloatingActionButton save;
    DatabaseReference reference;
    Leads leads;
    long maxid = 0;

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
        save = findViewById(R.id.save);
        leads = new Leads();                          // object creation
        reference = FirebaseDatabase.getInstance().getReference().child("leads");

//      code to get max id form the realtime database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    maxid = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//      adding resources into spinner from firebase
        List<String> resourceslist = new ArrayList<String>();
        resourceslist.add("Select Resource");        // this is default to show the title in the drop down menu
//      setting data into spinner using firebase
        resourceslist.add("Bed");
        resourceslist.add("Oxygen Cylinder");
        resourceslist.add("Plasma");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resourceslist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sources.setAdapter(dataAdapter);


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


//      save command
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//      fetch data from text fields
                String resource = sources.getSelectedItem().toString();
                String hospitalName = hospitalClinicName.getText().toString();
                String number = contactNo.getText().toString();
                String hospitalAddress = address.getText().toString();
                String city = cities.getSelectedItem().toString();
                String additionalDetails = otherDetails.getText().toString();
                String extraNote = note.getText().toString();

//              setting values to Leads class
                leads.setResource(resource);
                leads.setHospitalName(hospitalName);
                leads.setNumber(number);
                leads.setHospitalAddress(hospitalAddress);
                leads.setCity(city);
                leads.setAdditionalDetails(additionalDetails);
                leads.setExtraNote(extraNote);

                reference.child(String.valueOf(maxid+1)).setValue(leads).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddDetails.this, "Lead has been submitted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddDetails.this, "Lead haven't submitted", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}