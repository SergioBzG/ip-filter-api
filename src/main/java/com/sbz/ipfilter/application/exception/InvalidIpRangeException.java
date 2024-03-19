package com.sbz.ipfilter.application.exception;


public class InvalidIpRangeException extends RuntimeException {
    public InvalidIpRangeException(String message) {
        super(message);
    }
}
