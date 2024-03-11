package com.dailyfit.routine;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;

@JsonAutoDetect
public class Routine {
    int rid;
    String name;

    public Routine(int rid, String name) {
        this.rid = rid;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routine routine = (Routine) o;
        return rid == routine.rid && Objects.equals(name, routine.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rid, name);
    }

    @Override
    public String toString() {
        return "Routine:" +
                "rid='" + rid + '\'' +
                ", name=''" + name + '\'';
    }

    @JsonGetter("rid")
    public int rid() {
        return rid;
    }

    @JsonGetter("name")
    public String name() {
        return name;
    }
}
