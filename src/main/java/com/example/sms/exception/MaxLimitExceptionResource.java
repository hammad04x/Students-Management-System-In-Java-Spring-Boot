package com.example.sms.exception;

public class MaxLimitExceptionResource extends RuntimeException {

    public MaxLimitExceptionResource(String message) {
        super(message);
    }
}
