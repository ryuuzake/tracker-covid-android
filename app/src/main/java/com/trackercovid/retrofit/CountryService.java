package com.trackercovid.retrofit;

import com.trackercovid.api_response.CountryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CountryService {
    @GET("countries")
    Call<List<CountryResponse>> getAllCountries(@Query("sort") String sort);

    @GET("countries/{countryName}")
    Call<CountryResponse> getCountry(@Path("countryName") String countryName, @Query("query") String query);
}
