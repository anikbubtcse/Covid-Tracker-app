package com.anik.covidtrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.anik.covidtrackerapp.Bangladesh_Pojo_Class.MainJsonClass;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TextView confirmTotal, confirmIncrement, activeTotal, recoveredTotal, recoveredIncrement, deathTotal, deathIncrement, testTotal, updatedAt;
    private ApiService service;
    private TextView bangladeshTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();

        bangladeshTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CountryActivity.class);
                startActivity(intent);
            }
        });

        String Checker = getIntent().getStringExtra("check");
        if (Checker != null) {

            showGlobalStats();
        } else if (Checker == null) {

            showBangladeshStats();
        }
    }

    private void showGlobalStats() {

        String totalConfirmed = getIntent().getStringExtra("totalConfirmed");
        String totalActive = getIntent().getStringExtra("totalActive");
        String totalRecovered = getIntent().getStringExtra("totalRecovered");
        String totalDeath = getIntent().getStringExtra("totalDeath");
        String tests = getIntent().getStringExtra("tests");
        String countryName = getIntent().getStringExtra("countryName");


        pieChart.addPieSlice(new PieModel("Confirm", Integer.parseInt(totalConfirmed), getResources().getColor(R.color.yellow)));
        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(totalActive), getResources().getColor(R.color.blue)));
        pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(totalRecovered), getResources().getColor(R.color.green)));
        pieChart.addPieSlice(new PieModel("Death", Integer.parseInt(totalDeath), getResources().getColor(R.color.red)));

        pieChart.startAnimation();

        confirmTotal.setText(String.valueOf(totalConfirmed));
        activeTotal.setText(String.valueOf(totalActive));
        recoveredTotal.setText(String.valueOf(totalRecovered));
        deathTotal.setText(String.valueOf(totalDeath));
        testTotal.setText(String.valueOf(tests));
        bangladeshTv.setText(String.valueOf(countryName));


        String confirmedToday = getIntent().getStringExtra("confirmedToday");
        String recoveredToday = getIntent().getStringExtra("recoveredToday");
        String deathToday = getIntent().getStringExtra("deathToday");

        confirmIncrement.setText(String.valueOf("+" + confirmedToday));
        recoveredIncrement.setText(String.valueOf("+" + recoveredToday));
        deathIncrement.setText(String.valueOf("+" + deathToday));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        String currentDate = simpleDateFormat.format(calendar.getTime());

        updatedAt.setText("Updated at " + currentDate);

    }

    private void Init() {

        pieChart = findViewById(R.id.piechart);
        confirmTotal = findViewById(R.id.confirmed_total);
        confirmIncrement = findViewById(R.id.confirmed_increment);
        activeTotal = findViewById(R.id.active_total);
        recoveredTotal = findViewById(R.id.recovered_total);
        recoveredIncrement = findViewById(R.id.recovered_increment);
        deathTotal = findViewById(R.id.death_total);
        deathIncrement = findViewById(R.id.death_increment);
        testTotal = findViewById(R.id.test_total);
        updatedAt = findViewById(R.id.updated_at);
        bangladeshTv = findViewById(R.id.country_dropdown_tv);

    }

    private void showBangladeshStats() {

        service = ApiClient.getRetrofit().create(ApiService.class);
        Call<MainJsonClass> call = service.getBangladeshDetails("Bangladesh");
        call.enqueue(new Callback<MainJsonClass>() {
            @Override
            public void onResponse(Call<MainJsonClass> call, Response<MainJsonClass> response) {

                if (response.isSuccessful()) {
                    int totalConfirmed = response.body().getCases();
                    int totalActive = response.body().getActive();
                    int totalRecovered = response.body().getRecovered();
                    int totalDeath = response.body().getDeaths();
                    int tests = response.body().getTests();

                    pieChart.addPieSlice(new PieModel("Confirm", totalConfirmed, getResources().getColor(R.color.yellow)));
                    pieChart.addPieSlice(new PieModel("Active", totalActive, getResources().getColor(R.color.blue)));
                    pieChart.addPieSlice(new PieModel("Recovered", totalRecovered, getResources().getColor(R.color.green)));
                    pieChart.addPieSlice(new PieModel("Death", totalDeath, getResources().getColor(R.color.red)));

                    pieChart.startAnimation();

                    confirmTotal.setText(String.valueOf(totalConfirmed));
                    activeTotal.setText(String.valueOf(totalActive));
                    recoveredTotal.setText(String.valueOf(totalRecovered));
                    deathTotal.setText(String.valueOf(totalDeath));
                    testTotal.setText(String.valueOf(tests));


                    int confirmedToday = response.body().getTodayCases();
                    int recoveredToday = response.body().getTodayRecovered();
                    int deathToday = response.body().getTodayDeaths();

                    confirmIncrement.setText(String.valueOf("+" + confirmedToday));
                    recoveredIncrement.setText(String.valueOf("+" + recoveredToday));
                    deathIncrement.setText(String.valueOf("+" + deathToday));

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                    String currentDate = simpleDateFormat.format(calendar.getTime());

                    updatedAt.setText("Updated at " + currentDate);
                }
            }

            @Override
            public void onFailure(Call<MainJsonClass> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}