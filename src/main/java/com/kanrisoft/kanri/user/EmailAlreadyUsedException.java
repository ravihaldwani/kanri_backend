package com.kanrisoft.kanri.user;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException() {
        super("Email already used");
    }
}
