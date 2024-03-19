package com.sbz.ipfilter.application.validator;

import org.springframework.validation.BindingResult;

public interface IpChecker {
    void checkInvalidOrMissingData(BindingResult errors);
}
