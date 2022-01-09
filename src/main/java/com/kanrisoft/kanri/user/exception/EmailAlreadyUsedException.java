package com.kanrisoft.kanri.user.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super(String.format("Email %s already used", email));
    }
}
