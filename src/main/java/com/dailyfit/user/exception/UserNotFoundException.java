package com.dailyfit.user.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String email){
        super(String.format("User with email: '%s' does not exist", email));
    }
}
