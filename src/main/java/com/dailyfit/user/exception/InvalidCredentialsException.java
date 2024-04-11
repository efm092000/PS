package com.dailyfit.user.exception;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Email or password is incorrect.");
    }
}
