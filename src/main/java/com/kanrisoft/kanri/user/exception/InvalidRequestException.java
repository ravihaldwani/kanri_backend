package com.kanrisoft.kanri.user.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Invalid Request");
    }
}
