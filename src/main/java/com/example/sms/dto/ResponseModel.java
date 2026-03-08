package com.example.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class ResponseModel {
    private HttpStatus status;
    private int statusCode;
    private String message;
    private Object data;

    public ResponseModel(HttpStatus status, String message, Object data) {
        this.status = status;
        this.statusCode = this.status.value();
        this.message = message;
        this.data = data;
    }

    public static   ResponseModel created(String message, Object data) {
        return new ResponseModel(HttpStatus.CREATED, message, data);
    }
    public static ResponseModel success(String message, Object data) {
        return new ResponseModel(HttpStatus.OK, message, data);
    }

    public static ResponseModel not_found(String message, Object data) {
        return new ResponseModel(HttpStatus.NOT_FOUND, message, data);
    }
    public static ResponseModel conflict(String message, Object data) {
        return new ResponseModel(HttpStatus.CONFLICT, message, data);
    }
}
