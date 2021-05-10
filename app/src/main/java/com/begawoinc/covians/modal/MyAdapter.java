package com.begawoinc.covians.modal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    OnLeadListener onLeadListener;

    public MyAdapter(Context context, ArrayList<Leads> leads, OnLeadListener onLeadListener) {
        this.context = context;
        this.leads = leads;
        this.onLeadListener = onLeadListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v, onLeadListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Leads lead = leads.get(position);
        holder.resource.setText(lead.getResource());
        holder.hospitalName.setText(lead.getHospitalName());
        holder.hospitalAddress.setText(lead.getHospitalAddress());
        holder.city.setText(lead.getCity());
        holder.number.setText(lead.getNumber());

    }

    @Override
    public int getItemCount() {
        return leads.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView resource, hospitalName, hospitalAddress, city, number;
        OnLeadListener onLeadListener;

        public MyViewHolder(@NonNull View itemView, OnLeadListener onLeadListener) {
            super(itemView);

            resource = itemView.findViewById(R.id.resources_textView);
            hospitalName = itemView.findViewById(R.id.hospitalName_textView);
            hospitalAddress = itemView.findViewById(R.id.address_textView);
            city = itemView.findViewById(R.id.city_textView);
            number = itemView.findViewById(R.id.number);
            this.onLeadListener = onLeadListener;

//          setting onClickListener on call button
            number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                  intent to open dilar
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    String num_tel = "tel:" + number.getText().toString();
                    intent.setData(Uri.parse(num_tel));
                    v.getContext().startActivity(intent);

                }
            });


        }

        @Override
        public void onClick(View v) {
            onLeadListener.onLeadClick(getAdapterPosition());
        }
    }

    public interface OnLeadListener{
        void onLeadClick(int position);
    }

}
