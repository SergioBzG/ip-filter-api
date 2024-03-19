package com.sbz.ipfilter.application.exception;


public class RuleDoesNotExistException extends RuntimeException {
    public RuleDoesNotExistException(Long id) {
        super("Rule with id " + id + " does not exist");
    }
}
