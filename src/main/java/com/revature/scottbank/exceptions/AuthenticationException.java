package com.revature.scottbank.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("The email and/or password you provided do not match our " +
                "records");
    }
}
