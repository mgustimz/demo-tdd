package com.learn.demotdd.exception;

public class NoDataException extends RuntimeException {

    public static final int ERROR_CODE = 455;

    public NoDataException(String message) {
        super(message);
    }

}
