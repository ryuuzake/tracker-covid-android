package com.trackercovid.api_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryInfoResponse {
    @SerializedName("_id")
    @Expose
    private Integer id;
    @SerializedName("iso2")
    @Expose
    private String iso2;
    @SerializedName("iso3")
    @Expose
    private String iso3;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("long")
    @Expose
    private Double _long;
    @SerializedName("flag")
    @Expose
    private String flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLong() {
        return _long;
    }

    public void setLong(Double _long) {
        this._long = _long;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryInfoResponse)) return false;

        CountryInfoResponse that = (CountryInfoResponse) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (iso2 != null ? !iso2.equals(that.iso2) : that.iso2 != null) return false;
        if (iso3 != null ? !iso3.equals(that.iso3) : that.iso3 != null) return false;
        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;
        if (_long != null ? !_long.equals(that._long) : that._long != null) return false;
        return flag != null ? flag.equals(that.flag) : that.flag == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
