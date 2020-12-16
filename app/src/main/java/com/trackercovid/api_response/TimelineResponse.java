package com.trackercovid.api_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class TimelineResponse {

    @SerializedName("cases")
    @Expose
    private HashMap<String, Integer> cases;
    @SerializedName("deaths")
    @Expose
    private HashMap<String, Integer> deaths;
    @SerializedName("recovered")
    @Expose
    private HashMap<String, Integer> recovered;

    public HashMap<String, Integer> getCases() {
        return cases;
    }

    public void setCases(HashMap<String, Integer> cases) {
        this.cases = cases;
    }

    public HashMap<String, Integer> getDeaths() {
        return deaths;
    }

    public void setDeaths(HashMap<String, Integer> deaths) {
        this.deaths = deaths;
    }

    public HashMap<String, Integer> getRecovered() {
        return recovered;
    }

    public void setRecovered(HashMap<String, Integer> recovered) {
        this.recovered = recovered;
    }
}
