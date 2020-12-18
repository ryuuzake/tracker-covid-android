package com.trackercovid.retrofit;

import com.trackercovid.api_response.HistoricalResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HistoricalService {

    @GET("historical/{countryName}")
    Call<HistoricalResponse> getHistoricalData(@Path("countryName") String countryName);
}
