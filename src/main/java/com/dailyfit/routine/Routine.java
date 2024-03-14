package com.dailyfit.routine;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;

@JsonAutoDetect
public class Routine {
    int rid;
    String name;
    String email;

    public Routine(int rid, String name, String email) {
        this.rid = rid;
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routine routine = (Routine) o;
        return rid == routine.rid && Objects.equals(name, routine.name) && Objects.equals(email, routine.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rid, name, email);
    }

    @Override
    public String toString() {
        return "Routine{" +
                "rid=" + rid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @JsonGetter("rid")
    public int rid() {
        return rid;
    }

    @JsonGetter("name")
    public String name() {
        return name;
    }

    @JsonGetter("email")
    public String email() {
        return email;
    }
}
