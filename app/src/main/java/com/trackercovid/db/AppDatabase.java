package com.trackercovid.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.trackercovid.model.Country;

@Database(entities = {Country.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
}
