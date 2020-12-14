package com.trackercovid.api_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryResponse {
    @SerializedName("updated")
    @Expose
    private Long updated;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("countryInfo")
    @Expose
    private CountryInfoResponse countryInfo;
    @SerializedName("cases")
    @Expose
    private Integer cases;
    @SerializedName("todayCases")
    @Expose
    private Integer todayCases;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("todayDeaths")
    @Expose
    private Integer todayDeaths;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;
    @SerializedName("todayRecovered")
    @Expose
    private Integer todayRecovered;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("critical")
    @Expose
    private Integer critical;
    @SerializedName("casesPerOneMillion")
    @Expose
    private Integer casesPerOneMillion;
    @SerializedName("deathsPerOneMillion")
    @Expose
    private Double deathsPerOneMillion;
    @SerializedName("tests")
    @Expose
    private Integer tests;
    @SerializedName("testsPerOneMillion")
    @Expose
    private Integer testsPerOneMillion;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("continent")
    @Expose
    private String continent;
    @SerializedName("oneCasePerPeople")
    @Expose
    private Integer oneCasePerPeople;
    @SerializedName("oneDeathPerPeople")
    @Expose
    private Integer oneDeathPerPeople;
    @SerializedName("oneTestPerPeople")
    @Expose
    private Integer oneTestPerPeople;
    @SerializedName("activePerOneMillion")
    @Expose
    private Double activePerOneMillion;
    @SerializedName("recoveredPerOneMillion")
    @Expose
    private Double recoveredPerOneMillion;
    @SerializedName("criticalPerOneMillion")
    @Expose
    private Double criticalPerOneMillion;

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CountryInfoResponse getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(CountryInfoResponse countryInfo) {
        this.countryInfo = countryInfo;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(Integer todayCases) {
        this.todayCases = todayCases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(Integer todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(Integer todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(Integer casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public Double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(Double deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public Integer getTests() {
        return tests;
    }

    public void setTests(Integer tests) {
        this.tests = tests;
    }

    public Integer getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(Integer testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Integer getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public void setOneCasePerPeople(Integer oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public Integer getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public void setOneDeathPerPeople(Integer oneDeathPerPeople) {
        this.oneDeathPerPeople = oneDeathPerPeople;
    }

    public Integer getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public void setOneTestPerPeople(Integer oneTestPerPeople) {
        this.oneTestPerPeople = oneTestPerPeople;
    }

    public Double getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setActivePerOneMillion(Double activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public Double getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(Double recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public Double getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(Double criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryResponse)) return false;

        CountryResponse that = (CountryResponse) o;

        if (updated != null ? !updated.equals(that.updated) : that.updated != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (countryInfo != null ? !countryInfo.equals(that.countryInfo) : that.countryInfo != null)
            return false;
        if (cases != null ? !cases.equals(that.cases) : that.cases != null) return false;
        if (todayCases != null ? !todayCases.equals(that.todayCases) : that.todayCases != null)
            return false;
        if (deaths != null ? !deaths.equals(that.deaths) : that.deaths != null) return false;
        if (todayDeaths != null ? !todayDeaths.equals(that.todayDeaths) : that.todayDeaths != null)
            return false;
        if (recovered != null ? !recovered.equals(that.recovered) : that.recovered != null)
            return false;
        if (todayRecovered != null ? !todayRecovered.equals(that.todayRecovered) : that.todayRecovered != null)
            return false;
        if (active != null ? !active.equals(that.active) : that.active != null) return false;
        if (critical != null ? !critical.equals(that.critical) : that.critical != null)
            return false;
        if (casesPerOneMillion != null ? !casesPerOneMillion.equals(that.casesPerOneMillion) : that.casesPerOneMillion != null)
            return false;
        if (deathsPerOneMillion != null ? !deathsPerOneMillion.equals(that.deathsPerOneMillion) : that.deathsPerOneMillion != null)
            return false;
        if (tests != null ? !tests.equals(that.tests) : that.tests != null) return false;
        if (testsPerOneMillion != null ? !testsPerOneMillion.equals(that.testsPerOneMillion) : that.testsPerOneMillion != null)
            return false;
        if (population != null ? !population.equals(that.population) : that.population != null)
            return false;
        if (continent != null ? !continent.equals(that.continent) : that.continent != null)
            return false;
        if (oneCasePerPeople != null ? !oneCasePerPeople.equals(that.oneCasePerPeople) : that.oneCasePerPeople != null)
            return false;
        if (oneDeathPerPeople != null ? !oneDeathPerPeople.equals(that.oneDeathPerPeople) : that.oneDeathPerPeople != null)
            return false;
        if (oneTestPerPeople != null ? !oneTestPerPeople.equals(that.oneTestPerPeople) : that.oneTestPerPeople != null)
            return false;
        if (activePerOneMillion != null ? !activePerOneMillion.equals(that.activePerOneMillion) : that.activePerOneMillion != null)
            return false;
        if (recoveredPerOneMillion != null ? !recoveredPerOneMillion.equals(that.recoveredPerOneMillion) : that.recoveredPerOneMillion != null)
            return false;
        return criticalPerOneMillion != null ? criticalPerOneMillion.equals(that.criticalPerOneMillion) : that.criticalPerOneMillion == null;
    }

    @Override
    public int hashCode() {
        int result = updated != null ? updated.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (countryInfo != null ? countryInfo.hashCode() : 0);
        result = 31 * result + (cases != null ? cases.hashCode() : 0);
        result = 31 * result + (todayCases != null ? todayCases.hashCode() : 0);
        result = 31 * result + (deaths != null ? deaths.hashCode() : 0);
        result = 31 * result + (todayDeaths != null ? todayDeaths.hashCode() : 0);
        result = 31 * result + (recovered != null ? recovered.hashCode() : 0);
        result = 31 * result + (todayRecovered != null ? todayRecovered.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (critical != null ? critical.hashCode() : 0);
        result = 31 * result + (casesPerOneMillion != null ? casesPerOneMillion.hashCode() : 0);
        result = 31 * result + (deathsPerOneMillion != null ? deathsPerOneMillion.hashCode() : 0);
        result = 31 * result + (tests != null ? tests.hashCode() : 0);
        result = 31 * result + (testsPerOneMillion != null ? testsPerOneMillion.hashCode() : 0);
        result = 31 * result + (population != null ? population.hashCode() : 0);
        result = 31 * result + (continent != null ? continent.hashCode() : 0);
        result = 31 * result + (oneCasePerPeople != null ? oneCasePerPeople.hashCode() : 0);
        result = 31 * result + (oneDeathPerPeople != null ? oneDeathPerPeople.hashCode() : 0);
        result = 31 * result + (oneTestPerPeople != null ? oneTestPerPeople.hashCode() : 0);
        result = 31 * result + (activePerOneMillion != null ? activePerOneMillion.hashCode() : 0);
        result = 31 * result + (recoveredPerOneMillion != null ? recoveredPerOneMillion.hashCode() : 0);
        result = 31 * result + (criticalPerOneMillion != null ? criticalPerOneMillion.hashCode() : 0);
        return result;
    }
}
