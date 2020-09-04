package com.learn.demotdd.handler;

import com.learn.demotdd.exception.NoDataException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseHandler {

    @ExceptionHandler(value = NoDataException.class)
    public ResponseEntity<ErrorMessage> handle(Exception e) {
        ErrorMessage errorMessage = new ErrorMessage(NoDataException.ERROR_CODE, e.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @RequiredArgsConstructor
    @Getter
    private static class ErrorMessage {
        private final int errorCode;
        private final String message;
    }
}
