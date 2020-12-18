package com.trackercovid.model;

import java.util.Date;
import java.util.HashMap;

public class Historical {
    private String countryName;
    private HashMap<Date, Integer> cases;
    private HashMap<Date, Integer> deaths;
    private HashMap<Date, Integer> recovered;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

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
