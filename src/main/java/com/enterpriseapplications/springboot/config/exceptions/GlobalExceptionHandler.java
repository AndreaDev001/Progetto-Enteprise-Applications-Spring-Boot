package com.enterpriseapplications.springboot.config.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class,MissingItem.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> missingItemException(NoSuchElementException exception, HttpServletRequest request) {
        return errorResponse(HttpStatus.NOT_FOUND,Date.from(Instant.now()),exception.getLocalizedMessage(),request.getRequestURI());
    }
    @ExceptionHandler({HttpMessageNotReadableException.class, InvalidFormat.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> badRequestException(HttpMessageNotReadableException exception,HttpServletRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST,Date.from(Instant.now()),exception.getLocalizedMessage(), request.getRequestURI());
    }
    public ResponseEntity<ErrorResponse> errorResponse(HttpStatus httpStatus, Date date, String message, String url) {
        ErrorResponse errorResponse = new ErrorResponse(date,url,String.valueOf(httpStatus.value()),message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}