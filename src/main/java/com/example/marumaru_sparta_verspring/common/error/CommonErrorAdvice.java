package com.example.marumaru_sparta_verspring.common.error;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonErrorAdvice {

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<ErrorResponse> errorHandler(BindException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getAllErrors().get(0).getDefaultMessage(), 400)
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> errorHandler(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getMessage(), 400)
        );
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<ErrorResponse> errorHandler(NullPointerException e){
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getMessage(), 400)
        );
    }
}
