package com.sbz.ipfilter.application.exception;

import com.sbz.ipfilter.application.utils.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdviser {

    @ExceptionHandler(InvalidOrMissingDataException.class)
    public ResponseEntity<Response> invalidOrMissingDataExceptionHandler(InvalidOrMissingDataException exception) {
        return new ResponseEntity<>(
                Response.builder()
                        .message(exception.getMessage())
                        .status(false)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidIpRangeException.class)
    public ResponseEntity<Response> invalidIpRangeExceptionHandler(InvalidIpRangeException exception) {
        return new ResponseEntity<>(
                Response.builder()
                        .message(exception.getMessage())
                        .status(false)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuleDoesNotExistException.class)
    public ResponseEntity<Response> ruleDoesNotExistExceptionHandler(RuleDoesNotExistException exception) {
        return new ResponseEntity<>(
                Response.builder()
                        .message(exception.getMessage())
                        .status(false)
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exceptionHandler(Exception exception) {
        return new ResponseEntity<>(
                Response.builder()
                        .message(exception.getMessage())
                        .status(false)
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
