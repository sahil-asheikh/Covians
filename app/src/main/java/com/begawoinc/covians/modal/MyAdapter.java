package com.begawoinc.covians.modal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.begawoinc.covians.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Leads> leads;

    public MyAdapter(Context context, ArrayList<Leads> leads) {
        this.context = context;
        this.leads = leads;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Leads lead = leads.get(position);
        holder.resource.setText(lead.getResource());
        holder.hospitalName.setText(lead.getHospitalName());
        holder.hospitalAddress.setText(lead.getHospitalAddress());
        holder.city.setText(lead.getCity());
    }

    @Override
    public int getItemCount() {
        return leads.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView resource, hospitalName, hospitalAddress, city;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            resource = itemView.findViewById(R.id.resources_textView);
            hospitalName = itemView.findViewById(R.id.hospitalName_textView);
            hospitalAddress = itemView.findViewById(R.id.address_textView);
            city = itemView.findViewById(R.id.city_textView);

        }
    }
}
