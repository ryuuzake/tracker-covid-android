package com.trackercovid.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.trackercovid.model.Country;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Country.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(1);
    public static final ExecutorService databaseReadExecutor =
            Executors.newFixedThreadPool(1);

    public abstract CountryDao countryDao();
}
