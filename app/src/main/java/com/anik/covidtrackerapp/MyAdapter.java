package com.anik.covidtrackerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anik.covidtrackerapp.Country_Pojo_Class.MainCountryClass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<MainCountryClass> countryList;
    private Context context;
    private int id = 0;

    public MyAdapter(List<MainCountryClass> countryList, Context context) {
        this.countryList = countryList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.country_item_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        MainCountryClass mainCountryClass = countryList.get(position);
        holder.nameTv.setText(String.valueOf(mainCountryClass.getCountry()));
        holder.totalCasesTv.setText(String.valueOf(mainCountryClass.getCases()));
        holder.idTv.setText(String.valueOf(++id));
        Picasso.get().load(mainCountryClass.getCountryInfo().getFlag()).into(holder.countryFlag);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("check", "country");
                intent.putExtra("totalConfirmed", mainCountryClass.getCases().toString());
                intent.putExtra("totalActive", mainCountryClass.getActive().toString());
                intent.putExtra("totalRecovered", mainCountryClass.getRecovered().toString());
                intent.putExtra("totalDeath", mainCountryClass.getDeaths().toString());
                intent.putExtra("tests", mainCountryClass.getTests().toString());

                intent.putExtra("confirmedToday", mainCountryClass.getTodayCases().toString());
                intent.putExtra("recoveredToday", mainCountryClass.getTodayRecovered().toString());
                intent.putExtra("deathToday", mainCountryClass.getTodayDeaths().toString());
                intent.putExtra("countryName", mainCountryClass.getCountry().toString());


                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView countryFlag;
        private TextView idTv, nameTv, totalCasesTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            countryFlag = itemView.findViewById(R.id.country_flag);
            nameTv = itemView.findViewById(R.id.country_name_tv);
            totalCasesTv = itemView.findViewById(R.id.total_cases_tv);
            idTv = itemView.findViewById(R.id.id_tv);

        }
    }

    public void updateList(List<MainCountryClass> list) {

        countryList = list;
        notifyDataSetChanged();

    }
}
