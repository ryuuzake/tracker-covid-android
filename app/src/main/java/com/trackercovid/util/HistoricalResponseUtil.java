package com.trackercovid.util;

import com.trackercovid.api_response.HistoricalResponse;
import com.trackercovid.model.Historical;

public class HistoricalResponseUtil {
    public Historical toHistoricalModel(HistoricalResponse data) {
        Historical historical = new Historical();
        historical.setCountryName(data.getCountry());
        historical.setCases(data.getTimeline().getCases());
        historical.setDeaths(data.getTimeline().getDeaths());
        historical.setRecovered(data.getTimeline().getRecovered());
        return historical;
    }
}
