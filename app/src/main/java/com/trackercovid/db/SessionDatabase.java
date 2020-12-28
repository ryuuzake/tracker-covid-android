package com.trackercovid.db;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class SessionDatabase<T> {
    protected final SharedPreferences sharedPrefs;

    public SessionDatabase(Context context) {
        sharedPrefs = context.getSharedPreferences(getSessionKey(), Context.MODE_PRIVATE);
    }

    protected abstract String getSessionKey();

    public T initialize(T data) {
        // save to shared preference
        setSessionData(data);

        // load from shared preference
        return getSessionData();
    }

    public void destroy() {
        sharedPrefs.edit().clear().apply();
    }

    public void update(T data) {
        destroy();
        setSessionData(data);
    }

    abstract public T getSessionData();

    abstract protected void setSessionData(T data);

}
