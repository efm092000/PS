package com.dailyfit.weekly;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;

@JsonAutoDetect
public class WeeklyPlan {
    private int wid;
    private String name;
    private final String email;

    public WeeklyPlan(String name, String email) {
        this.wid = 0;
        this.name = name;
        this.email = email;
    }

    public WeeklyPlan(int wid, String name, String email) {
        this.wid = wid;
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeeklyPlan wp = (WeeklyPlan) o;
        return Objects.equals(wid, wp.wid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wid,name);
    }

    @Override
    public String toString() {
        return "Weeklyplan:" +
                "wID='" + wid + '\'' +
                "name='" + name + "'";
    }

    @JsonGetter("wid")
    public int getWid() {
        return wid;
    }

    @JsonGetter("name")
    public String name() {
        return name;
    }

    @JsonGetter("email")
    public String email() {
        return email;
    }

    public void name(String name) {
        this.name = name;
    }

    public void wid(int wid) {
        this.wid = wid;
    }
}
