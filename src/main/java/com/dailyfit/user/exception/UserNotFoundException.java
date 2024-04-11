package com.dailyfit.user.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String email) {
        super(String.format("User with email <%s> not found.", email));
    }
}
