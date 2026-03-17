package com.example.sms.exception;

public class NotFoundExceptionResource extends RuntimeException {

    public NotFoundExceptionResource(String message) {
        super(message);
    }
}
