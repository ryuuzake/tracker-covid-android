package com.trackercovid.api_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoricalResponse {
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("province")
    @Expose
    private List<String> province = null;
    @SerializedName("timeline")
    @Expose
    private TimelineResponse timeline;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getProvince() {
        return province;
    }

    public void setProvince(List<String> province) {
        this.province = province;
    }

    public TimelineResponse getTimeline() {
        return timeline;
    }

    public void setTimeline(TimelineResponse timeline) {
        this.timeline = timeline;
    }
}
