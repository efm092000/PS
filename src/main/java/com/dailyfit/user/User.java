package com.dailyfit.user;

import java.util.Objects;

public class User {
    private final String name;
    private final String email;
    private final String password;

    public User(String email, String name, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
}
