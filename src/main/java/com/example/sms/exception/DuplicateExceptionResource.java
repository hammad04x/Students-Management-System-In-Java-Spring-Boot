package com.example.sms.exception;

public class DuplicateExceptionResource extends RuntimeException {

    public DuplicateExceptionResource(String message) {
        super(message);
    }
}
