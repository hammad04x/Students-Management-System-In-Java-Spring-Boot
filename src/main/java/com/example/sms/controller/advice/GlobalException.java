package com.example.sms.controller.advice;

import com.example.sms.dto.ResponseModel;
import com.example.sms.exception.DuplicateExceptionResource;
import com.example.sms.exception.MaxLimitExceptionResource;
import com.example.sms.exception.NotFoundExceptionResource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(DuplicateExceptionResource.class)
    public ResponseModel handleDuplicateExceptionResource(DuplicateExceptionResource exception) {
        return ResponseModel.conflict(
                exception.getMessage(),
                null
        );
    }

    @ExceptionHandler(NotFoundExceptionResource.class)
    public ResponseModel handleNotFoundExceptionResource(NotFoundExceptionResource exception) {
        return ResponseModel.not_found(
                exception.getMessage(),
                null
        );
    }
    @ExceptionHandler(MaxLimitExceptionResource.class)
    public ResponseModel handleMaxLimitExceptionResource(MaxLimitExceptionResource exception) {
        return ResponseModel.not_found(
                exception.getMessage(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseModel handleException(Exception exception) {
        return new ResponseModel(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                null

        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String fieldError = error.getDefaultMessage();
                    errors.put(fieldName, fieldError);
                });

        return new ResponseModel(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                errors
        );
    }
}
