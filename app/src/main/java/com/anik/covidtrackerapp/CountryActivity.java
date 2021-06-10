package com.anik.covidtrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anik.covidtrackerapp.Country_Pojo_Class.MainCountryClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private List<MainCountryClass> list;
    private ApiService service;
    private MyAdapter adapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        Init();
        getCountryStats();
    }

    private void getCountryStats() {

        service = ApiClient.getRetrofit().create(ApiService.class);
        Call<List<MainCountryClass>> call = service.getCountryDetails();

        call.enqueue(new Callback<List<MainCountryClass>>() {
            @Override
            public void onResponse(Call<List<MainCountryClass>> call, Response<List<MainCountryClass>> response) {

                if (response.isSuccessful()) {

                    list = response.body();
                    adapter = new MyAdapter(list, CountryActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<MainCountryClass>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void Init() {

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setBackgroundColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String data = newText.toLowerCase();
        List<MainCountryClass> newList = new ArrayList<>();

        for (MainCountryClass countryList : list) {

            if (countryList.getCountry().toLowerCase().contains(data)) {

                newList.add(countryList);
            }

        }

        adapter.updateList(newList);
        return true;
    }
}