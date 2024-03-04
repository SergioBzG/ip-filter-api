package com.sbz.ipfilter.application.exceptions;

public class RuleDoesNotExistException extends Exception {
    public RuleDoesNotExistException(Long id) {
        super("Rule with id " + id + " does not exist");
    }
}
