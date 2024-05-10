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

    private boolean admin;
    private String profilePicture;

    public User(String email, String password, String name, boolean premium, boolean admin, String profilePicture) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.premium = premium;
        this.admin = admin;
        this.profilePicture = profilePicture;
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
        return Objects.hash(name, email, password, premium);
    }

    @Override
    public String toString() {
        return "User:" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", premium='" + premium + '\'' +
                ", profilePictUrl='" + profilePicture + '\'' +
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
    public boolean premium() { return premium; }

    public void setPremium(boolean premium) { this.premium = premium; }

    @JsonGetter("isAdmin")
    public boolean admin() {return admin;}

    @JsonGetter("profilePicture")
    public String profilePicture() { return profilePicture;}

    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture;}

}
