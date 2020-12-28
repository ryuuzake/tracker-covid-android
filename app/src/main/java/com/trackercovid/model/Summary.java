package com.trackercovid.model;

public class Summary {
    private long active;
    private long cases;
    private long critical;
    private long deaths;
    private long recovered;

    public Summary() {
    }

    public Summary(long active, long cases, long critical, long deaths, long recovered) {
        this.active = active;
        this.cases = cases;
        this.critical = critical;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public long getCases() {
        return cases;
    }

    public void setCases(long cases) {
        this.cases = cases;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long getCritical() {
        return critical;
    }

    public void setCritical(long critical) {
        this.critical = critical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Summary)) return false;

        Summary summary = (Summary) o;

        if (getActive() != summary.getActive()) return false;
        if (getCases() != summary.getCases()) return false;
        if (getCritical() != summary.getCritical()) return false;
        if (getDeaths() != summary.getDeaths()) return false;
        return getRecovered() == summary.getRecovered();
    }

    @Override
    public int hashCode() {
        int result = (int) (getActive() ^ (getActive() >>> 32));
        result = 31 * result + (int) (getCases() ^ (getCases() >>> 32));
        result = 31 * result + (int) (getCritical() ^ (getCritical() >>> 32));
        result = 31 * result + (int) (getDeaths() ^ (getDeaths() >>> 32));
        result = 31 * result + (int) (getRecovered() ^ (getRecovered() >>> 32));
        return result;
    }
}
