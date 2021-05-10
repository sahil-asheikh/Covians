package com.begawoinc.covians;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.begawoinc.covians.modal.Leads;
import com.begawoinc.covians.modal.MyAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnLeadListener {

    Button search_btn;
    Spinner cities;
    FloatingActionButton fab;
    DatabaseReference reference, reference_recyclerView;
    ValueEventListener listener;
    RecyclerView leads_recyclerView;
    ArrayList<Leads> leads_arrayList;
    MyAdapter myAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      find view by id
        search_btn = findViewById(R.id.search_btn);
        cities = findViewById(R.id.cities);
        fab = findViewById(R.id.fab);
        leads_recyclerView = findViewById(R.id.leads_recyclerView);
        progressBar = findViewById(R.id.progressBar);
        reference = FirebaseDatabase.getInstance().getReference().child("cities");
        reference_recyclerView = FirebaseDatabase.getInstance().getReference().child("leads");


//      onclicklistener for fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDetails.class);
                startActivity(intent);
            }
        });

//      codes for recycler view
        leads_recyclerView.setHasFixedSize(true);
        leads_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        leads_arrayList = new ArrayList<>();
        myAdapter = new MyAdapter(this, leads_arrayList, this);
        leads_recyclerView.setAdapter(myAdapter);
        reference_recyclerView.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                leads_arrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Leads lead = dataSnapshot.getValue(Leads.class);
                    leads_arrayList.add(lead);
                    progressBar.setVisibility(View.INVISIBLE);              // to stop progressBar
                }
                myAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);              // to stop progressBar
                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

//      adding city into spinner from firebase
        List<String> citieslist = new ArrayList<String>();
        citieslist.clear();             // to clear pre-stored values
        citieslist.add("All");                      // this is default to show the title in the drop down menu
//      setting data into spinner using firebase
        listener = reference.addValueEventListener(new ValueEventListener() {
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

//      code to filter leads based on city
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
//              store value of spinner into city variable
                String city = cities.getSelectedItem().toString();
                if (city.equals("All")) {
                    progressBar.setVisibility(View.INVISIBLE);
                    reference_recyclerView.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            leads_arrayList.clear();
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                Leads lead = dataSnapshot.getValue(Leads.class);
                                leads_arrayList.add(lead);
                                progressBar.setVisibility(View.INVISIBLE);              // to stop progressBar
                            }
                            myAdapter.notifyDataSetChanged();

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressBar.setVisibility(View.INVISIBLE);              // to stop progressBar
                            Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
//              code to get city form the realtime database
                            reference_recyclerView.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot_rv) {
                                    leads_arrayList.clear();
                                    for (DataSnapshot dataSnapshot: snapshot_rv.getChildren()) {
                                            Leads lead = new Leads();
                                            lead.setResource(dataSnapshot.child("resource").getValue().toString());
                                            lead.setHospitalName(dataSnapshot.child("hospitalName").getValue().toString());
                                            lead.setNumber(dataSnapshot.child("number").getValue().toString());
                                            lead.setHospitalAddress(dataSnapshot.child("hospitalAddress").getValue().toString());
                                            lead.setCity(dataSnapshot.child("city").getValue().toString());
                                            lead.setAdditionalDetails(dataSnapshot.child("additionalDetails").getValue().toString());
                                            lead.setExtraNote(dataSnapshot.child("extraNote").getValue().toString());

//                                      condition to compare spinner city and lead's city
                                            if (city.equals(dataSnapshot.child("city").getValue().toString())) {
                                                leads_arrayList.add(lead);
                                                progressBar.setVisibility(View.INVISIBLE);              // to stop progressBar
                                            }

                                    }
                                    myAdapter.notifyDataSetChanged();

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    progressBar.setVisibility(View.INVISIBLE);              // to stop progressBar
                                    Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });

//      array adapter to add cities in spinner
        ArrayAdapter<String> citydataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, citieslist);
        citydataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(citydataAdapter);


    }

    @Override
    public void onLeadClick(int position) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, Lead_Info.class);
        startActivity(intent);
    }
}