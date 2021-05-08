package com.begawoinc.covians;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    ProgressBar progressBar;
    DatabaseReference reference, referenceResources, referenceCities;
    ValueEventListener listenerResources, listenerCities;
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
        progressBar = findViewById(R.id.progressBar);
        leads = new Leads();                          // object creation
        reference = FirebaseDatabase.getInstance().getReference().child("leads");       // for leads
        referenceResources = FirebaseDatabase.getInstance().getReference().child("resourses");       // for leads
        referenceCities = FirebaseDatabase.getInstance().getReference().child("cities");       // for leads

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
        resourceslist.clear();             // to clear prestored values
        resourceslist.add("Select Resource");               // this is default to show the title in the drop down menu
//      setting data into spinner using firebase
        listenerResources = referenceResources.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for loop to retrieve data from database
                for (DataSnapshot item: snapshot.getChildren()) {
                    resourceslist.add(item.getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, resourceslist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sources.setAdapter(dataAdapter);


//      adding city into spinner from firebase
        List<String> citieslist = new ArrayList<String>();
        citieslist.clear();             // to clear prestored values
        citieslist.add("Select city");                      // this is default to show the title in the drop down menu
//      setting data into spinner using firebase
        listenerCities = referenceCities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//              for loop to retrieve data from database
                for (DataSnapshot item: snapshot.getChildren()) {
                    citieslist.add(item.getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter<String> citydataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citieslist);
        citydataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(citydataAdapter);


//      save command
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//              make progressBar visible
                progressBar.setVisibility(View.VISIBLE);

//              fetch data from text fields
                String resource = sources.getSelectedItem().toString();
                String hospitalName = hospitalClinicName.getText().toString();
                String number = contactNo.getText().toString();
                String hospitalAddress = address.getText().toString();
                String city = cities.getSelectedItem().toString();
                String additionalDetails = otherDetails.getText().toString();
                String extraNote = note.getText().toString();

//              conditions for empty fields
                if (resource.equals("Select Resource")) {
                    Toast.makeText(AddDetails.this, "Please Select Resources", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(hospitalName)) {
                    Toast.makeText(AddDetails.this, "Please Enter Hospital Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(number)) {
                    Toast.makeText(AddDetails.this, "Please Enter Number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(hospitalAddress)) {
                    Toast.makeText(AddDetails.this, "Please Enter Hospital Address", Toast.LENGTH_SHORT).show();
                } else if (city.equals("Select city")) {
                    Toast.makeText(AddDetails.this, "Please Select City", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(additionalDetails)) {
                    Toast.makeText(AddDetails.this, "Please Enter Price", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(extraNote)) {
                    Toast.makeText(AddDetails.this, "Please Enter Verified date", Toast.LENGTH_SHORT).show();
                } else {

//                  setting values to Leads class
                    leads.setResource(resource);
                    leads.setHospitalName(hospitalName);
                    leads.setNumber(number);
                    leads.setHospitalAddress(hospitalAddress);
                    leads.setCity(city);
                    leads.setAdditionalDetails(additionalDetails);
                    leads.setExtraNote(extraNote);

//                  saving details to firebase
                    reference.child(String.valueOf(maxid+1)).setValue(leads).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressBar.setVisibility(View.INVISIBLE);      // to stop progressBar
                            Toast.makeText(AddDetails.this, "Lead has been submitted", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);      // to stop progressBar
                            Toast.makeText(AddDetails.this, "Lead haven't submitted", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

    }
}