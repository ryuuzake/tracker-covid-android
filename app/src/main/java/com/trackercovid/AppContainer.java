package com.trackercovid;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.room.Room;

import com.google.gson.GsonBuilder;
import com.trackercovid.db.AppDatabase;
import com.trackercovid.interactor.NetworkInfoSource;
import com.trackercovid.interactor.NetworkInfoSourceImpl;
import com.trackercovid.retrofit.CountryService;
import com.trackercovid.retrofit.HistoricalService;
import com.trackercovid.util.BuildVersionProviderImpl;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppContainer {

    private final static String baseUrl = "https://corona.lmao.ninja/v2/";

    public CountryService countryService = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient.Builder().build())
            .build()
            .create(CountryService.class);

    public HistoricalService historicalService = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .setDateFormat("MM/dd/yy")
                    .setPrettyPrinting()
                    .setVersion(1.0)
                    .create()))
            .client(new OkHttpClient.Builder().build())
            .build()
            .create(HistoricalService.class);

    public AppDatabase getAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "tracker-covid").build();
    }

    public NetworkInfoSource getNetworkInfoSource(Context context) {
        return new NetworkInfoSourceImpl(
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE),
                new BuildVersionProviderImpl()
        );
    }
}
