package com.trackercovid.api_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;

public class TimelineResponse {

    @SerializedName("cases")
    @Expose
    private HashMap<Date, Integer> cases;
    @SerializedName("deaths")
    @Expose
    private HashMap<Date, Integer> deaths;
    @SerializedName("recovered")
    @Expose
    private HashMap<Date, Integer> recovered;

    public HashMap<Date, Integer> getCases() {
        return cases;
    }

    public void setCases(HashMap<Date, Integer> cases) {
        this.cases = cases;
    }

    public HashMap<Date, Integer> getDeaths() {
        return deaths;
    }

    public void setDeaths(HashMap<Date, Integer> deaths) {
        this.deaths = deaths;
    }

    public HashMap<Date, Integer> getRecovered() {
        return recovered;
    }

    public void setRecovered(HashMap<Date, Integer> recovered) {
        this.recovered = recovered;
    }
}
