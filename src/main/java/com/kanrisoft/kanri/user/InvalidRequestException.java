package com.kanrisoft.kanri.user;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Invalid Request");
    }
}
