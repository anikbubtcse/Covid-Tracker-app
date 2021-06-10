package com.anik.covidtrackerapp;

import com.anik.covidtrackerapp.Bangladesh_Pojo_Class.MainJsonClass;
import com.anik.covidtrackerapp.Country_Pojo_Class.MainCountryClass;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

public interface ApiService {

    @GET("countries/{country}")
    Call<MainJsonClass> getBangladeshDetails(@Path("country") String c);

    @GET("countries")
    Call<List<MainCountryClass>> getCountryDetails();

}
