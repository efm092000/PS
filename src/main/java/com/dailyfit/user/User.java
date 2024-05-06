package com.dailyfit.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Objects;

@JsonAutoDetect
public class User {
    private final String email;
    private String name;
    private String password;
    private boolean premium;

    public User(String email, String password, String name, boolean premium) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.premium = premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }

    @Override
    public String toString() {
        return "User:" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + "'";
    }

    @JsonGetter("email")
    public String email() {
        return email;
    }

    @JsonGetter("password")
    public String password() {
        return password;
    }

    public void password(String password) {
        this.password = password;
    }

    @JsonGetter("name")
    public String name() {
        return name;
    }

    public void name(String name) {
        this.name = name;
    }

    @JsonGetter("isPremium")
    public Boolean premium() { return premium; }

    public void setPremium(boolean premium) { this.premium = premium; }
}
