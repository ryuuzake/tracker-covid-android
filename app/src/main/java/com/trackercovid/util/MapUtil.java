package com.trackercovid.util;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.trackercovid.model.Country;

import java.util.ArrayList;
import java.util.List;

public class MapUtil {
    public static List<WeightedLatLng> toWeightedLatLngs(List<Country> data) {
        List<WeightedLatLng> result = new ArrayList<>();
        for (Country country : data) {
            result.add(new WeightedLatLng(
                    new LatLng(country.getLatitude(), country.getLongitude()), country.getCases())
            );
        }
        return result;
    }

    public static List<LatLng> toLatLngs(List<Country> data) {
        List<LatLng> result = new ArrayList<>();
        for (Country country : data) {
            result.add(new LatLng(country.getLatitude(), country.getLongitude()));
        }
        return result;
    }
}
