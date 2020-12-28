package com.trackercovid.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.trackercovid.model.Summary;

public class SummaryDatabase extends SessionDatabase<Summary> {
    protected String SESSION_KEY = "SessionSummary";

    public SummaryDatabase(Context context) {
        super(context);
    }

    @Override
    protected String getSessionKey() {
        return SESSION_KEY;
    }

    @Override
    public Summary getSessionData() {
        String sessionDataJson = sharedPrefs.getString(SESSION_KEY, null);
        if (sessionDataJson != null) {
            return new Gson().fromJson(sessionDataJson, Summary.class);
        }
        return null;
    }

    @Override
    protected void setSessionData(Summary data) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(SESSION_KEY, new Gson().toJson(data));
        editor.apply();
    }
}
