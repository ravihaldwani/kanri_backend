package com.kanrisoft.kanri.user.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String email) {
        super(String.format("Value (%s) not a valid email", email));
    }
}
