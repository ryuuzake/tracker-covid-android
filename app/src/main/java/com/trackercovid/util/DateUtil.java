package com.trackercovid.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getDay(Date date) {
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("EEEE");
        return (date != null) ? dateFormat.format(date) : null;
    }

    public static String getDate(Date date) {
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return (date != null) ? dateFormat.format(date) : null;
    }

    public static String getFullDate(Date date) {
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        return (date != null) ? dateFormat.format(date) : null;
    }

    public static String getTime(Date date) {
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("hh:MM");
        return (date != null) ? dateFormat.format(date) : "-- : --";
    }
}
